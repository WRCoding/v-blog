package top.lpepsi.vblog.vdo;

import lombok.Data;

/**
 * @program: v-blog
 * @description: 点赞DO
 * @author: 林北
 * @create: 2020-02-25 10:28
 **/
@Data
public class LikeDO {
    private Integer id;
    private String username;
    private Integer articleId;
    private Integer likeStatus;

    public LikeDO() {
    }

    public LikeDO(String username, Integer articleId, Integer likeStatus) {
        this.username = username;
        this.articleId = articleId;
        this.likeStatus = likeStatus;
    }
}
