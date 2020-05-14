package top.lpepsi.vblog.service.admin;

import org.apache.ibatis.annotations.Param;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.dto.Response;

/**
 * @program: v-blog
 * @description: 管理
 * @author: 林北
 * @create: 2020-03-29 10:37
 **/
public interface AdminService {

    /**
    * @Description: 获取用户的所有文章
    * @Param: [username]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-03-29
    */
    Response blogManager(String username, Integer pageNum);
    
    /**
    * @Description: 获取用户的个人数据
    * @Param: [username]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-03-30
    */
    Response personalData(String username);

    /**
     * @Description: 文章编辑
     * @Param: [articleId]
     * @return: top.lpepsi.vblog.dto.Response
     * @Author: 林北
     * @Date: 2020-04-01
     */
    Response editByArticleId(Integer articleId);
    
    /**
    * @Description: 删除博客
    * @Param: [articleId]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-05-08
    */
    Response deleteBlog(Integer articleId);
    
    /**
    * @Description: 更新文章
    * @Param: [edit]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-04-03
    */
    Response updateBlog(Edit edit);
    
    /**
    * @Description: 用户所有文章评论
    * @Param: [author]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-04-04
    */
    Response getPersonalComment(String author,Integer pageNum);
    
    /**
    * @Description: 删除评论
    * @Param: [commentId]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-05-12
    */
    Response deleteComment(Integer commentId);
    
    /**
    * @Description: 修改密码
    * @Param: [newPassword]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-04-06
    */
    Response changPwd(String newPassword);
    
    /**
    * @Description: 每日更新所有用户的博客数
    * @Param: []
    * @return: void
    * @Author: 林北
    * @Date: 2020-05-01
    */
    void updateRecord();

    /**
    * @Description: 每日更新所有用户的浏览量
    * @Param: []
    * @return: void
    * @Author: 林北
    * @Date: 2020-05-01
    */
    void updateRecordView();

    /**
    * @Description: 根据key获取对应用户的记录
    * @Param: [username, key]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-05-02
    */
    Response getRecord(String username,String key);
}
