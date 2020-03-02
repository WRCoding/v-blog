package top.lpepsi.vblog.vdo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: v-blog
 * @description: 题图类
 * @author: 林北
 * @create: 2020-02-14 12:06
 **/
@Data
public class ImageDO implements Serializable {
    private static final long serialVersionUID = -3705397208216225116L;

    private Integer id;
    private Integer articleId;
    private String imageUrl;
    private Date createBy;
    private Date modifiedBy;
}
