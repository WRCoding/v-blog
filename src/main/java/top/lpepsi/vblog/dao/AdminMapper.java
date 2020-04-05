package top.lpepsi.vblog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.vdo.CommentDO;

import java.util.List;

/**
 * @program: v-blog
 * @description: 管理
 * @author: 林北
 * @create: 2020-03-29 10:39
 **/
@Mapper
public interface AdminMapper {

    /**
    * @Description: 获取用户的所有文章
    * @Param: [username, offest, limit]
    * @return: java.util.List<top.lpepsi.vblog.dto.Detail>
    * @Author: 林北
    * @Date: 2020-03-29
    */
    List<Detail> blogManager (@Param("username") String username);

    /**
    * @Description: 获取用户父评论个数
    * @Param: [username]
    * @return: int
    * @Author: 林北
    * @Date: 2020-03-30
    */
    int getCommentNum(String username);
    
    /**
    * @Description: 获取用户子评论个数
    * @Param: [username]
    * @return: int
    * @Author: 林北
    * @Date: 2020-03-30
    */
    int getCommentChileNum(String username);
    
    /**
    * @Description: 获取用户文章数
    * @Param: [username]
    * @return: int
    * @Author: 林北
    * @Date: 2020-03-30
    */
    int getBlogNum(String username);
    
    /**
    * @Description: 获取用户访问量
    * @Param: [username]
    * @return: int
    * @Author: 林北
    * @Date: 2020-03-30
    */
    int getViewNum(String username);
    
    /**
    * @Description: 更新文章内容
    * @Param: [edit]
    * @return: int
    * @Author: 林北
    * @Date: 2020-04-03
    */
    int updateBlogContent(Edit edit);
    
    /**
    * @Description: 更新文章标题
    * @Param: [edit]
    * @return: int
    * @Author: 林北
    * @Date: 2020-04-03
    */
    int updateBlogTitle(Edit edit);

    List<CommentDO> getPersonalComment(String author);
}
