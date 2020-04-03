package top.lpepsi.vblog.service.blog;

import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.dto.Response;

import java.util.List;

/**
 * @program: v-blog
 * @description: 博客
 * @author: 林北
 * @create: 2020-02-18 11:21
 **/
public interface BlogService {

    /**
    * @Description: 根据当前页返回文章列表
    * @Param: [currentPage]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-02-18
    */
    Response blogs(Integer currentPage);
    
    /**
    * @Description: 根据文章ID查询文章详情
    * @Param: [articleId]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-02-18
    */
    Response findBlog(Integer articleId);

    /**
    * @Description: 保存文章
    * @Param: [edit]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-02-19
    */
    Response saveBlog2DB(Edit edit);

    /**
    * @Description: 获取最新文章
    * @Param: []
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-02-21
    */
    Response<List<Detail>> getRecentBlogs();

    /**
    * @Description: 获取访问最多五篇博客
    * @Param: []
    * @return: top.lpepsi.vblog.dto.Response<java.util.List<top.lpepsi.vblog.dto.Detail>>
    * @Author: 林北
    * @Date: 2020-02-21
    */
    Response<List<Detail>> getMostViewBlog();
    
    /**
    * @Description: 保存每篇文章的访问量
    * @Param: []
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-28
    */
    void saveBlogView2DB();


}
