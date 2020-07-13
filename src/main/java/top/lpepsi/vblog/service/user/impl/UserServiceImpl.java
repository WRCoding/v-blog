package top.lpepsi.vblog.service.user.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lpepsi.vblog.config.RabbitmqConfig;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Response register(UserDO userDO) {
        try {
            userDO.setRole("ROLE_USER");
            userDO.setStatus(1);
            userDO.setPassword(new BCryptPasswordEncoder().encode(userDO.getPassword()));
            int count = userMapper.register(userDO);
            if (count == 1) {
                redisUtil.zSetPut(RedisKeyConstant.USER_VIEW, userDO.getUserName(), 0D);
                return Response.success("注册成功");
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
    @RabbitListener(queues = RabbitmqConfig.QUEUE)
    public void sendEmail(String message) {
        int captcha = (int) ((Math.random()*9+1)*1000);
        LOGGER.info("message: "+message+", captcha: "+captcha);
//        String username = message.getUserName();
//        String email = message.getEmail();
//        if (email == null) {
//            email = getEmailByUserName(username);
//        }
//        String uuid = UUID.randomUUID().toString();
//        String activeCode = uuid + "*" + username + "*" + email;
//        String address = email;
        try {
            redisUtil.valuePut(String.valueOf(captcha), message,60*5L,TimeUnit.SECONDS);
            mailUtil.sendMail(message, String.valueOf(captcha));
        } catch (MailException | MessagingException e) {
            redisUtil.delete(String.valueOf(captcha));
            LOGGER.error("邮箱发送失败： "+e.getMessage());
        }
    }

    @Override
    public Response activeCode(String code) {
        boolean hasKey = redisUtil.hasKey(code);
        LOGGER.info("haskey: "+hasKey);
        if (hasKey){
//            String email = (String) redisUtil.valueGet(code);
//            redisUtil.delete(code);
            return Response.success();
        }else {
            return Response.failure("验证码不正确");
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

    @Override
    public Response captcha(String emailAddress) {
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,RabbitmqConfig.ROUTE_KEY, emailAddress);
        return Response.success("验证码已经发送");
    }
}
