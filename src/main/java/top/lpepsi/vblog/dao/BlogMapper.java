package top.lpepsi.vblog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.vdo.ArticleDO;

import java.util.List;

/**
 * @program: v-blog
 * @description: 博客Dao层
 * @author: 林北
 * @create: 2020-02-18 09:40
 **/
@Mapper
public interface BlogMapper {

    /**
    * @Description: 返回文章列表
    * @Param: []
    * @return: java.util.List<top.lpepsi.vblog.vdo.ArticleDO>
    * @Author: 林北
    * @Date: 2020-02-18
    */
    List<ArticleDO> blogs();
    
    /**
    * @Description: 根据文章ID查询文章详情
    * @Param: [blogId]
    * @return: top.lpepsi.vblog.dto.Detail
    * @Author: 林北
    * @Date: 2020-02-18
    */
    Detail findBlog(Integer blogId);
    
    /**
    * @Description: 返回详细文章列表
    * @Param: []
    * @return: java.util.List<top.lpepsi.vblog.dto.Detail>
    * @Author: 林北
    * @Date: 2020-02-18
    */
    List<Detail> detailBlogs();
    
    /**
    * @Description: 保存文章
    * @Param: [edit]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-19
    */
    void saveBlog2DB(Edit edit);

    /**
    * @Description: 保存文章内容
    * @Param: [edit]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-19
    */
    void saveContent2DB(Edit edit);

    /**
    * @Description: 保存文章题图
    * @Param: [edit]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-19
    */
    void saveImage2DB(Edit edit);

    /**
    * @Description: 通过标签查找标签下的所有博客
    * @Param: [tagName]
    * @return: java.util.List<top.lpepsi.vblog.dto.Detail>
    * @Author: 林北
    * @Date: 2020-02-23
    */
    List<Detail> findBlogByTagName(String tagName);

    /**
    * @Description: 保存每篇文章的访问量
    * @Param: [articleId, num]
    * @return: int
    * @Author: 林北
    * @Date: 2020-02-28
    */
    int saveBlogView(@Param("articleId") String articleId, @Param("num") double num);
}
