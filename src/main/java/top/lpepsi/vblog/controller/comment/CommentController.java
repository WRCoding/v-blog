package top.lpepsi.vblog.controller.comment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.lpepsi.vblog.dao.AdminMapper;
import top.lpepsi.vblog.dao.CommentMapper;
import top.lpepsi.vblog.dto.Comment;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.comment.impl.CommentServiceImpl;
import top.lpepsi.vblog.vdo.CommentDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: v-blog
 * @description: 评论接口
 * @author: 林北
 * @create: 2020-02-19 23:31
 **/
@Controller
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private AdminMapper adminMapper;

    @GetMapping("/comment/{articleId}")
    @ResponseBody
    public Response getComments(@PathVariable("articleId")Integer articleId){
        return Response.success(commentService.getCommentsByArticleId(articleId));
    }

    @PostMapping("/saveComment")
    @ResponseBody
    public Response saveComment(@RequestBody Comment comment){
        return commentService.saveComment(comment);
    }

}
