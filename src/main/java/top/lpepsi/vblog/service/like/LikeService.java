package top.lpepsi.vblog.service.like;

import top.lpepsi.vblog.vdo.LikeCount;
import top.lpepsi.vblog.vdo.LikeDO;

import java.util.List;

/**
 * @program: v-blog
 * @description: 点赞
 * @author: 林北
 * @create: 2020-02-25 10:19
 **/
public interface LikeService {


    /**
    * @Description: 获取username在articleId的点赞状态
    * @Param: [username, articleId]
    * @return: java.lang.Integer
    * @Author: 林北
    * @Date: 2020-02-25
    */
    Integer getLikeStatus(String username,Integer articleId);

    /**
    * @Description: 根据用户名和文章ID获得点赞状态
    * @Param: [username, articleId]
    * @return: top.lpepsi.vblog.vdo.LikeDO
    * @Author: 林北
    * @Date: 2020-02-25
    */
    LikeDO getLikeStatusByUserNameAndArticleId(String username, Integer articleId);


    /**
    * @Description: 文章点赞
    * @Param: [username, articleId]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-25
    */
    void Liked2Redis(String username,Integer articleId);

    /**
    * @Description: 取消点赞
    * @Param: [username, articleId]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-25
    */
    void unLiked2Redis(String username,Integer articleId);

    /**
    * @Description: 根据文章ID获取文章的点赞数
    * @Param: [articleId]
    * @return: java.lang.Integer
    * @Author: 林北
    * @Date: 2020-02-25
    */
    Integer getLikedCountByIdFromRedis(Integer articleId);

    /**
    * @Description: 把每篇文章点赞数据保存到数据库
    * @Param: []
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-28
    */
    void saveLikedCount2DB();

    /**
    * @Description: 把用户点赞数据保存到数据库
    * @Param: []
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-28
    */
    void saveUserLiked2DB();
}
