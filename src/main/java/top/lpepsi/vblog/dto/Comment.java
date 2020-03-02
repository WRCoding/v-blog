package top.lpepsi.vblog.dto;

import lombok.Data;
import top.lpepsi.vblog.vdo.CommentDO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: v-blog
 * @description: 评论
 * @author: 林北
 * @create: 2020-02-27 09:26
 **/
@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 421024019509518904L;

    private Integer id;
    private Integer articleId;
    private String commentName;
    private String replyName;
    private String comment;
    private String createBy;
    private Integer parentId;


    private boolean showReplayComments = false;

    private List<Comment> childComments;
}
