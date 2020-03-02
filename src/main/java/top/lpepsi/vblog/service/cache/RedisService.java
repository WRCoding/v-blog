package top.lpepsi.vblog.service.cache;

import top.lpepsi.vblog.dto.Blog;
import top.lpepsi.vblog.dto.Detail;

import java.util.List;
import java.util.Map;

/**
 * @program: v-blog
 * @description: 缓存
 * @author: 林北
 * @create: 2020-02-18 14:56
 **/
public interface RedisService {

    /**
    * @Description: 将所有文章保存到Redis中
    * @Param: [blogs]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-18
    */
    void firstSaveBlogs2Redis(List<Detail> blogs);

    /**
    * @Description: 将文章保存到Redis
    * @Param: [detail]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-18
    */
    void saveBlog2Redis(Detail detail);

    /**
     * 根据文章ID从Redis获取信息
     * @param key
     * @return
     */
    Detail getBlogFromRedis(String key);

    /**
    * @Description: 获取分页数据
    * @Param: [currentPage]
    * @return: java.util.List<top.lpepsi.vblog.dto.Blog>
    * @Author: 林北
    * @Date: 2020-02-21
    */
    Map<String,Object> pagination(Long currentPage);

    /**
    * @Description: 获取最新文章
    * @Param: []
    * @return: java.util.List<top.lpepsi.vblog.dto.Detail>
    * @Author: 林北
    * @Date: 2020-02-21
    */
    List<Detail> getRecentBlogsFromRedis();
    
    /**
    * @Description: 获取访问量最多的五篇文章
    * @Param: []
    * @return: java.util.List<top.lpepsi.vblog.dto.Detail>
    * @Author: 林北
    * @Date: 2020-02-21
    */
    List<Detail> getMostViewBlog();
}
