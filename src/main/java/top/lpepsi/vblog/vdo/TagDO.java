package top.lpepsi.vblog.vdo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: v-blog
 * @description: 标签类
 * @author: 林北
 * @create: 2020-02-14 11:16
 **/
@Data
public class TagDO implements Serializable {
    private static final long serialVersionUID = 2798840706906326578L;

    private Integer id;
    private String tagName;
    private Integer tagNumber;
    private Date createBy;
    private Date modifiedBy;
    private Integer isEffective;

    public TagDO() {
    }

    public TagDO(String tagName) {
        this.tagName = tagName;
    }
}
