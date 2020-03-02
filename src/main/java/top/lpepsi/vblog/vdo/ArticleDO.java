package top.lpepsi.vblog.vdo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: v-blog
 * @description: 文章类
 * @author: 林北
 * @create: 2020-02-14 11:40
 **/
@Data
public class ArticleDO implements Serializable {
    private static final long serialVersionUID = -924511271889328364L;

    private Integer id;
    private String author;
    private String articleTitle;
    private Integer articleViews;
    private String articleContent;
    private Date createBy;
    private Integer isDelete;
    private Integer likeNum;
    private String titleImage;

    private List<TagDO> tags;
    private List<CommentDO> commentDOList;
}
