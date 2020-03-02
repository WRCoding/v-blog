package top.lpepsi.vblog.dao;

import org.apache.ibatis.annotations.Mapper;
import top.lpepsi.vblog.dto.Comment;
import top.lpepsi.vblog.vdo.CommentDO;

import java.util.List;

/**
 * @program: v-blog
 * @description: 评论Dao
 * @author: 林北
 * @create: 2020-02-26 21:18
 **/
@Mapper
public interface CommentMapper {

    /**
    * @Description: 获取指定文章的评论信息
    * @Param: []
    * @return: java.util.List<top.lpepsi.vblog.vdo.CommentDO>
    * @Author: 林北
    * @Date: 2020-02-26
    */
    List<CommentDO> getCommentsByArticleId(Integer articleId);

    /**
    * @Description: 保存评论
    * @Param: [comment]
    * @return: int
    * @Author: 林北
    * @Date: 2020-02-28
    */
    int saveComment(Comment comment);
}
