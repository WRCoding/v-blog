package top.lpepsi.vblog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lpepsi.vblog.vdo.UserDO;

/**
 * @program: v-blog
 * @description: 用户Dao
 * @author: 林北
 * @create: 2020-02-17 11:12
 **/
@Mapper
public interface UserMapper {
    /**
    * @Description: 根据用户名查找用户
    * @Param: [userName]
    * @return: top.lpepsi.vblog.vdo.UserDO
    * @Author: 林北
    * @Date: 2020-02-17
    */
    UserDO findUserByuserName(String userName);

    /**
    * @Description: 用户注册
    * @Param: [userDO]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-17
    */
    int register(UserDO userDO);
    
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
    * @return: int
    * @Author: 林北
    * @Date: 2020-02-17
    */
    int updateStatus(String username);

    /**
    * @Description: 将用户被访问量保存到数据库
    * @Param: [username, view]
    * @return: int
    * @Author: 林北
    * @Date: 2020-02-29
    */
    int saveUserView2DB(@Param("username")String username, @Param("view") double view);

    /**
    * @Description: 检查注册信息是否重复
    * @Param: [email, name]
    * @return: int
    * @Author: 林北
    * @Date: 2020-02-29
    */
    int checkInfoEmail(@Param("email")String email);

    /**
    * @Description: 检查注册信息是否重复
    * @Param: [name]
    * @return: int
    * @Author: 林北
    * @Date: 2020-02-29
    */
    int checkInfoName(@Param("name") String name);

}
