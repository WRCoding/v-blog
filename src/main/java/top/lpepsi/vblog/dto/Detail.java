package top.lpepsi.vblog.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import top.lpepsi.vblog.vdo.CommentDO;
import top.lpepsi.vblog.vdo.TagDO;

import java.io.Serializable;
import java.util.List;

/**
 * @program: v-blog
 * @description: 详情博客DTO
 * @author: 林北
 * @create: 2020-02-14 12:10
 **/
@Data
@Document(indexName = "vblog_search")
public class Detail implements Serializable {
    private static final long serialVersionUID = -4161593163652056525L;
    @Id
    private Integer id;
    @Field(type = FieldType.Keyword)
    private String author;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String articleTitle;
    private Integer articleViews;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String articleContent;
    private String createBy;
    private Integer likeNum;

    private String titleImage;
    private List<CommentDO> commentDOList;
    private List<TagDO> tags;
}
