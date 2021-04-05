package top.lpepsi.vblog.controller.loginandreg;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.user.impl.UserServiceImpl;
import top.lpepsi.vblog.utils.MailUtil;
import top.lpepsi.vblog.vdo.ResultCode;
import top.lpepsi.vblog.vdo.UserDO;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: v-blog
 * @description: 登录和注册接口
 * @author: 林北
 * @create: 2020-02-15 11:48
 **/
@Controller
public class LoginAndRegController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAndRegController.class);

    @Autowired
    private UserServiceImpl userService;



    @PostMapping("/register")
    @ResponseBody
    public Response register(@RequestBody UserDO userDO){
//        UserDO userDO = new UserDO(email, password, username);
        LOGGER.info("userInfo: {}",userDO.toString());
        return userService.register(userDO);
    }
//    @PostMapping("/activate")
//    @ResponseBody
//    public Response<Integer> activate(@RequestParam("email")String email,@RequestParam("username") String username,@RequestParam("password")String password) {
//        int success = userService.sendEmail(email, username);
//        return Response.success(success);
//    }
    @GetMapping("/activeCode")
    @ResponseBody
    public Response activeCode(String code){
        return userService.activeCode(code);
    }

    @GetMapping("/captcha")
    @ResponseBody
    public Response captcha(@RequestParam("emailAddress") String emailAddress){
        return userService.captcha(emailAddress);
    }

    @PostMapping("/check")
    @ResponseBody
    public Response check(@RequestParam("email")String email,@RequestParam("name")String name){
        return userService.checkInfo(email, name);
    }

    @GetMapping("/auth")
    @ResponseBody
    public Response auth(){
        return null;
    }
}
