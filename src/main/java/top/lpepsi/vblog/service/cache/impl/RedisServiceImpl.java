package top.lpepsi.vblog.service.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lpepsi.vblog.constant.RedisKeyConstant;
import top.lpepsi.vblog.dao.BlogMapper;
import top.lpepsi.vblog.dto.Blog;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.service.cache.RedisService;
import top.lpepsi.vblog.utils.RedisUtil;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: v-blog
 * @description: 缓存
 * @author: 林北
 * @create: 2020-02-18 14:58
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class RedisServiceImpl implements RedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private  RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BlogMapper blogMapper;

    private ZSetOperations<String,Object> zSetOperations;
    private HashOperations hashOperations ;
    private ListOperations listOperations;

    /**
     * 只执行一次，在构造函数之后执行
     */
    @PostConstruct
    public void init(){
        zSetOperations = redisTemplate.opsForZSet();
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
    }

    @Override
    public void firstSaveBlogs2Redis(List<Detail> blogs) {
        if (null == blogs){
            LOGGER.error("firstSaveBlogs2Redis出错，blogs为空");
            return;
        }
        for (Detail blog : blogs) {
            if(!redisUtil.hashHasKey(RedisKeyConstant.BLOG,String.valueOf(blog.getId()))){
                redisUtil.listPushRight(RedisKeyConstant.BLOG_LIST, String.valueOf(blog.getId()));
                redisUtil.hashPut(RedisKeyConstant.BLOG,String.valueOf(blog.getId()) ,blog);
                redisUtil.zSetPut(RedisKeyConstant.VIEW, String.valueOf(blog.getId()), Double.valueOf(blog.getArticleViews()));
            }
        }
    }

    @Override
    public void saveBlog2Redis(Detail detail){
        if(null == detail){
            LOGGER.error("saveBlog2Redis发生异常：detail == null");
            return;
        }
        if (!redisUtil.hashHasKey(RedisKeyConstant.BLOG,String.valueOf(detail.getId()))){
            redisUtil.listPushLeft(RedisKeyConstant.BLOG_LIST, String.valueOf(detail.getId()));
            redisUtil.hashPut(RedisKeyConstant.BLOG,String.valueOf(detail.getId()) ,detail);
            redisUtil.zSetPut(RedisKeyConstant.VIEW, String.valueOf(detail.getId()), Double.valueOf(detail.getArticleViews()));
        }
    }

    @Override
    public Detail getBlogFromRedis(String key) {
        Detail detail = (Detail) redisUtil.hashGet(RedisKeyConstant.BLOG, key);
        //获取访问量
        Double views = redisUtil.zSetGetScore(RedisKeyConstant.VIEW, key);
        double number = views;
        //设置访问量
        detail.setArticleViews((int)number);
        return detail;
    }
    @Override
    public Map<String,Object> pagination(Long currentPage) {
        Map<String,Object> map = null;
        try {
            long start;
            long end;
            if (currentPage == 1){
                start = 0L;
                end = 3L;
            }else {
                start = currentPage*4-4;
                end = start+3;
            }
            List list = redisUtil.listGetRange(RedisKeyConstant.BLOG_LIST, start, end);
            List pagination = (List) list.stream().map(id -> {
                Detail detail = (Detail) redisUtil.hashGet(RedisKeyConstant.BLOG, String.valueOf(id));
                return detail;
            }).collect(Collectors.toList());
            map = new HashMap<>((int)(2/0.75F+1));
            map.put("total", redisUtil.size(RedisKeyConstant.BLOG_LIST));
            map.put("pagination",pagination);
        } catch (Exception e) {
           LOGGER.error("获取分页失败： "+e.getMessage());
        }
        return map;
    }

    @Override
    public List<Detail> getRecentBlogsFromRedis() {
        try {
            List<String> list = redisUtil.listGetRange(RedisKeyConstant.BLOG_LIST, 0L, 4L);
            List<Detail> blogs = (List) list.stream().map(id -> {
                Detail detail = (Detail) redisUtil.hashGet(RedisKeyConstant.BLOG, String.valueOf(id));
                return detail;
            }).collect(Collectors.toList());
            return blogs;
        } catch (Exception e) {
            LOGGER.error("getRecentBlogsFromRedis发生异常： "+e.getMessage());
        }
        return null;
    }

    @Override
    public List<Detail> getMostViewBlog() {
        try {
            Set<Object> ids = redisUtil.zSetReverseRange(RedisKeyConstant.VIEW, 0L, 4L);
            List<Detail> blogs = (List) ids.stream().map(id -> {
                Detail detail = (Detail) redisUtil.hashGet(RedisKeyConstant.BLOG, String.valueOf(id));
                return detail;
            }).collect(Collectors.toList());
            return blogs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
