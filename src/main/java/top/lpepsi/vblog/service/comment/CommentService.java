package top.lpepsi.vblog.service.comment;

import top.lpepsi.vblog.dto.Comment;
import top.lpepsi.vblog.dto.Response;

/**
 * @program: v-blog
 * @description: 评论
 * @author: 林北
 * @create: 2020-02-27 10:53
 **/
public interface CommentService {
    
    /**
    * @Description: 获取文章的评论列表
    * @Param: [articleId]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-02-27
    */
    Response getCommentsByArticleId(Integer articleId);

    /**
    * @Description: 保存评论
    * @Param: [comment]
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-02-28
    */
    Response saveComment(Comment comment);
}
