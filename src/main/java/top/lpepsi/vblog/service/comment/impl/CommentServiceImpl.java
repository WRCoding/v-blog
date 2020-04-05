package top.lpepsi.vblog.service.comment.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lpepsi.vblog.dao.CommentMapper;
import top.lpepsi.vblog.dto.Comment;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.comment.CommentService;
import top.lpepsi.vblog.utils.DateUtil;
import top.lpepsi.vblog.vdo.CommentDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: v-blog
 * @description: 评论
 * @author: 林北
 * @create: 2020-02-27 10:54
 **/
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private DateUtil dateUtil;

    @Override
    public Response getCommentsByArticleId(Integer articleId) {
        if (articleId == null){
            return Response.failure("articleId不能为空");
        }
        try {
            List<CommentDO> list = commentMapper.getCommentsByArticleId(articleId);
            List<Comment> commentList = new ArrayList<>();
            for (CommentDO commentDO : list) {
                Comment comment = new Comment();
                BeanUtils.copyProperties(commentDO,comment,"modifiedBy","isDelete","createBy");
                List<CommentDO> childCommentDO;
                if ((childCommentDO = commentDO.getChildComments()) != null){
                    List<Comment> childComments = new ArrayList<>();
                    for (CommentDO aDo : childCommentDO) {
                        Comment childComment = new Comment();
                        BeanUtils.copyProperties(aDo,childComment,"modifiedBy","isDelete","createBy");
                        childComment.setCreateBy(DateUtil.date2String(aDo.getCreateBy(), "yyyy-MM-dd HH:mm:ss"));
                        childComments.add(childComment);
                    }
                    comment.setChildComments(childComments);
                }
                comment.setCreateBy(DateUtil.date2String(commentDO.getCreateBy(), "yyyy-MM-dd HH:mm:ss"));
                commentList.add(comment);
            }
            return Response.success(commentList);
        }catch (Exception e){
            LOGGER.error("getCommentsByArticleId发生异常： "+e.getMessage());
            return Response.failure("获取评论列表失败");
        }
    }

    @Override
    public Response saveComment(Comment comment) {
        int index = commentMapper.saveComment(comment);
        if (index == 1){
            return Response.success("评论成功");
        }else {
            return Response.failure("评论失败");
        }
    }
}
