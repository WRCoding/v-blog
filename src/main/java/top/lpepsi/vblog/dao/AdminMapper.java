package top.lpepsi.vblog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.vdo.CommentDO;
import top.lpepsi.vblog.vdo.RecordDO;

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
    * @Description: 删除博客
    * @Param: [articleId]
    * @return: java.lang.Integer
    * @Author: 林北
    * @Date: 2020-05-08
    */
    Integer deleteBlog(Integer articleId);

    /**
    * @Description: 删除评论
    * @Param: [commentId]
    * @return: java.lang.Integer
    * @Author: 林北
    * @Date: 2020-05-12
    */
    Integer deleteComment(Integer commentId);

    /**
    * @Description: 对所删除博客所属的标签数量减一
    * @Param: [tagNameList]
    * @return: void
    * @Author: 林北
    * @Date: 2020-05-10
    */
    void changTagNum(@Param("list") List tagNameList);

    /**
    * @Description: 获取用户父评论个数
    * @Param: [username]
    * @return: int
    * @Author: 林北
    * @Date: 2020-03-30
    */
    Integer getCommentNum(String username);
    
    /**
    * @Description: 获取用户子评论个数
    * @Param: [username]
    * @return: int
    * @Author: 林北
    * @Date: 2020-03-30
    */
    Integer getCommentChileNum(String username);
    
    /**
    * @Description: 获取用户文章数
    * @Param: [username]
    * @return: int
    * @Author: 林北
    * @Date: 2020-03-30
    */
    Integer getBlogNum(String username);
    
    /**
    * @Description: 获取用户访问量
    * @Param: [username]
    * @return: int
    * @Author: 林北
    * @Date: 2020-03-30
    */
    Integer getViewNum(String username);
    
    /**
    * @Description: 更新文章内容
    * @Param: [edit]
    * @return: int
    * @Author: 林北
    * @Date: 2020-04-03
    */
    Integer updateBlogContent(Edit edit);
    
    /**
    * @Description: 更新文章标题
    * @Param: [edit]
    * @return: int
    * @Author: 林北
    * @Date: 2020-04-03
    */
    Integer updateBlogTitle(Edit edit);

    /**
    * @Description: 获得用户文章的所有评论
    * @Param: [author]
    * @return: java.util.List<top.lpepsi.vblog.vdo.CommentDO>
    * @Author: 林北
    * @Date: 2020-04-06
    */
    List<CommentDO> getPersonalComment(String author);

    /**
    * @Description: 修改密码
    * @Param: [username, newPassWord]
    * @return: java.lang.Integer
    * @Author: 林北
    * @Date: 2020-04-06
    */
    Integer changPwd(String username,String newPassWord);

    /**
    * @Description: 获取所有用户名
    * @Param: []
    * @return: java.util.List<java.lang.String>
    * @Author: 林北
    * @Date: 2020-05-01
    */
    List<String> getUserName();

    /**
    * @Description: 更新所有用户每日的博客数
    * @Param: [recordList]
    * @return: void
    * @Author: 林北
    * @Date: 2020-05-01
    */
    void updateRecordNum(List recordDOList);
    
    /**
    * @Description: 每日更新所有用户的浏览量
    * @Param: [recordDOList]
    * @return: void
    * @Author: 林北
    * @Date: 2020-05-01
    */
    void updateRecordView(List recordDOList);

    /**
    * @Description: 根据key获取对应用户的记录
    * @Param: [username, tableName]
    * @return: java.util.List<top.lpepsi.vblog.vdo.RecordDO>
    * @Author: 林北
    * @Date: 2020-05-02
    */
    List<RecordDO> getRecord(@Param("username")String username,@Param("tableName")String tableName);
}
