package top.lpepsi.vblog.vdo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: v-blog
 * @description: 点赞数量
 * @author: 林北
 * @create: 2020-02-28 14:39
 **/
@Data
public class LikeCount implements Serializable {
    private static final long serialVersionUID = 6762431387229271022L;

    private Integer articleId;
    private Integer likeNum;


    public LikeCount() {
    }

    public LikeCount(Integer articleId, Integer likeNum) {
        this.articleId = articleId;
        this.likeNum = likeNum;
    }
}
