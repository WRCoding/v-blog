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
}
