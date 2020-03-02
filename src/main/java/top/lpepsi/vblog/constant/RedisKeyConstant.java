package top.lpepsi.vblog.constant;

/**
 * @program: v-blog
 * @description: RedisKey值常量
 * @author: 林北
 * @create: 2020-02-18 14:50
 **/
public class RedisKeyConstant {
    public static final String USER_LIKE = "USER_LIKE";
    public static final String LIKE_COUNT = "LIKE_COUNT";
    public static final String HISTORICAL_VIEWS = "HISTORICAL_VIEWS";
    public static final String BLOG_NUM = "BLOG_NUM";
    public static final String LIKE_NUM = "LIKE_NUM";
    public static final String BLOG = "BLOG_INFO";
    public static final String VIEW = "BLOG_VIEW";
    public static final String BLOG_LIST = "BLOG_LIST";
    public static final String TAGS = "TAGS";
    public static final String TAG_BLOG = "TAG_BLOG";
    public static final String USER_VIEW = "USER_VIEW";

    public static String getLikeKey(String username,Integer articleId){
        StringBuilder builder = new StringBuilder();
        builder.append(username);
        builder.append(":");
        builder.append(articleId);
        return builder.toString();
    }

    private RedisKeyConstant() {
    }
}
