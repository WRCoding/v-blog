package top.lpepsi.vblog.dto;

import lombok.Data;
import top.lpepsi.vblog.vdo.CommentDO;
import top.lpepsi.vblog.vdo.TagDO;

import java.io.Serializable;
import java.util.List;

/**
 * @program: v-blog
 * @description: Blog大概信息
 * @author: 林北
 * @create: 2020-02-18 10:47
 **/
@Data
public class Blog implements Serializable {
    private static final long serialVersionUID = -920019368659390806L;

    private Integer id;
    private String author;
    private String articleTitle;
    private Integer articleViews;
    private String createBy;
    private Integer likeNum;
    private String titleImage;
    private List<TagDO> tags;
}
