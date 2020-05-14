package top.lpepsi.vblog.service.user.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lpepsi.vblog.constant.RedisKeyConstant;
import top.lpepsi.vblog.dao.UserMapper;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.jwt.JwtLoginFilter;
import top.lpepsi.vblog.service.user.UserService;
import top.lpepsi.vblog.utils.MailUtil;
import top.lpepsi.vblog.utils.RedisUtil;
import top.lpepsi.vblog.vdo.UserDO;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @program: v-blog
 * @description: 用户Impl类
 * @author: 林北
 * @create: 2020-02-17 16:30
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Response register(UserDO userDO) {
        try {
            userDO.setRole("ROLE_USER");
            userDO.setPassword(new BCryptPasswordEncoder().encode(userDO.getPassword()));
            int count = userMapper.register(userDO);
            if (count == 1) {
                if (sendEmail(userDO.getEmail(), userDO.getUserName()) == 1){
                    return Response.success("注册成功,请前往邮箱激活账号");
                }else {
                    return Response.failure("注册失败,邮箱不存在");
                }
            } else {
                return Response.failure("注册失败");
            }
        } catch (Exception e) {
            LOGGER.error("注册功能异常：" + e.getMessage());
            return Response.failure();
        }
    }

    @Override
    public String getEmailByUserName(String username) {
        String email = userMapper.getEmailByUserName(username);
        return email;
    }

    @Override
    public void updateStatus(String username) {
        userMapper.updateStatus(username);
    }

    @Override
    public int sendEmail(String email, String username) {
        if (email == null) {
            email = getEmailByUserName(username);
        }
        String uuid = UUID.randomUUID().toString();
        String activeCode = uuid + "*" + username + "*" + email;
        String address = email;
        Future<Integer> submit = threadPool.submit(() -> {
            try {
                redisUtil.valuePut(activeCode, username,60*5L,TimeUnit.SECONDS);
                mailUtil.sendMail(address, activeCode);
                return 1;
            } catch (MailException e) {
                redisUtil.delete(activeCode);
                LOGGER.error("邮箱不存在： "+e.getMessage());
                return 0;
            }
        });
        int success = 1;
        try {
            success = submit.get();
            LOGGER.info("success: "+success);
        } catch (Exception e) {
            LOGGER.error("Exception: "+e.getMessage());
        }
        return success;
    }

    @Override
    public Response activeCode(String code) {
        if (redisUtil.hasKey(code)){
            String username = (String) redisUtil.valueGet(code);
            updateStatus(username);
            redisUtil.delete(code);
            redisUtil.zSetPut(RedisKeyConstant.USER_VIEW, username, 1D);
            return Response.success();
        }else {
            String[] strs = code.split("\\*");
            for (String str : strs) {
                System.out.println(str);
            }
            String username = strs[1];
            String email = strs[2];
            HashMap<String,String> info = new HashMap<>(2);
            info.put("username", username);
            info.put("email", email);
            return Response.failure(info);
        }
    }

    @Override
    public void saveUserView2DB() {
        Set<ZSetOperations.TypedTuple<String>> set = redisUtil.zSetReverseRangeWithScores(RedisKeyConstant.USER_VIEW,0L,-1L);
        set.forEach(tuple ->{
            String username = tuple.getValue();
            double view = tuple.getScore() == null?0:tuple.getScore();
            userMapper.saveUserView2DB(username, view);
        });
    }

    @Override
    public Response checkInfo(String email, String name) {
        if (name.length() == 0){
            int count = userMapper.checkInfoEmail(email);
            return Response.success(count);
        }else if (email.length() == 0){
            int count = userMapper.checkInfoName(name);
            return Response.success(count);
        }else {
            return Response.failure();
        }
    }
}
