package top.lpepsi.vblog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lpepsi.vblog.vdo.LikeCount;
import top.lpepsi.vblog.vdo.LikeDO;

/**
 * @program: v-blog
 * @description: 点赞Dao
 * @author: 林北
 * @create: 2020-02-25 10:29
 **/
@Mapper
public interface LikeMapper {

    /**
    * @Description: 获取username在articleId的点赞状态
    * @Param: [username, articleId]
    * @return: top.lpepsi.vblog.vdo.LikeDO
    * @Author: 林北
    * @Date: 2020-02-25
    */
    LikeDO getLikeStatus(@Param("username")String username, @Param("articleId")Integer articleId);

    /**
    * @Description: 从数据库获得文章点赞数
    * @Param: [articleId]
    * @return: int
    * @Author: 林北
    * @Date: 2020-02-25
    */
    int getLikedCountByIdFromDB(Integer articleId);

    /**
    * @Description: 把点赞数据保存到数据库
    * @Param: [likeCount]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-28
    */
    int saveLikedCount2DB(LikeCount likeCount);

    /**
    * @Description: 把用户点赞数据保存到数据库
    * @Param: [likeDO]
    * @return: int
    * @Author: 林北
    * @Date: 2020-02-28
    */
    int saveUserLikedStatus2DB(LikeDO likeDO);
    
    /**
    * @Description: 更新用户的点赞数据
    * @Param: [likeDO]
    * @return: int
    * @Author: 林北
    * @Date: 2020-02-28
    */
    int updateUserLikedStatus2DB(LikeDO likeDO);
}
