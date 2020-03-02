package top.lpepsi.vblog.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: v-blog
 * @description: 发布，编辑博客DTO
 * @author: 林北
 * @create: 2020-02-14 11:04
 **/
@Data
public class Edit extends Detail implements Serializable  {
    private static final long serialVersionUID = 8054186131190690305L;
    /**
     * 博客ID
     */
    private Integer id;
    /**
     * 博客标签
     */
    private String[] tagArray;
    /**
     *博客作者
     */
    private String author;
    /**
     * 博客题图
     */
    private String titleImageUrl;
    /**
     * 博客内容
     */
    private String content;

    /**
     * 题目
     */
    private String title;
}
