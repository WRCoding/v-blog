package top.lpepsi.vblog.vdo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: v-blog
 * @description: 评论类
 * @author: 林北
 * @create: 2020-02-14 11:21
 **/
@Data
public class CommentDO implements Serializable {
    private static final long serialVersionUID = 6187473511501801804L;

    private Integer id;
    private Integer articleId;
    private String commentName;
    private String replyName;
    private String comment;
    private Date createBy;
    private Date modifiedBy;
    private Integer isDelete;
    private Integer parentId;
    private List<CommentDO> childComments;
}
