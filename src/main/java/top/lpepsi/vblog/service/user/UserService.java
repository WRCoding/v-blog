package top.lpepsi.vblog.service.user;

import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.vdo.UserDO;

/**
 * @program: v-blog
 * @description: 用户类
 * @author: 林北
 * @create: 2020-02-17 16:28
 **/
public interface UserService {

    /**
    * @Description: 用户注册
    * @Param: [userDO]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-02-17
    */
     Response register(UserDO userDO);

     /**
     * @Description: 通过用户名获得邮箱
     * @Param: [username]
     * @return: java.lang.String
     * @Author: 林北
     * @Date: 2020-02-17
     */
    String getEmailByUserName(String username);

    /**
    * @Description: 更新用户账号状态
    * @Param: [username]
    * @return: 
    * @Author: 林北
    * @Date: 2020-02-17
    */
    void updateStatus(String username);

    /**
    * @Description: 发送邮件
    * @Param: [email, activeCode]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-18
    */
    void sendEmail(String message);

    /**
    * @Description: 激活账号
    * @Param: [code]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-02-18
    */
    Response activeCode(String code);
    
    /**
    * @Description: 保存每个用户的被访问量
    * @Param: []
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-28
    */
    void saveUserView2DB();

    /**
    * @Description: 检查注册信息是否重复
    * @Param: [email, name]
    * @return: int
    * @Author: 林北
    * @Date: 2020-02-29
    */
    Response checkInfo(String email,String name);


    /**
    * @Description: 发送验证码到指定邮箱
    * @Param: [emailAddress]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-07-13
    */
    Response captcha(String emailAddress);
}
