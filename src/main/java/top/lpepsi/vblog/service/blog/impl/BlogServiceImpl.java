package top.lpepsi.vblog.service.blog.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lpepsi.vblog.constant.RedisKeyConstant;
import top.lpepsi.vblog.dao.BlogMapper;
import top.lpepsi.vblog.dto.Blog;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.blog.BlogService;
import top.lpepsi.vblog.service.cache.impl.RedisServiceImpl;
import top.lpepsi.vblog.service.es.impl.EsBlogServiceImpl;
import top.lpepsi.vblog.service.like.impl.LikeServiceImpl;
import top.lpepsi.vblog.service.tag.impl.TagServiceImpl;
import top.lpepsi.vblog.utils.MarkDownToHtmlUtil;
import top.lpepsi.vblog.utils.RedisUtil;
import top.lpepsi.vblog.vdo.ArticleDO;
import top.lpepsi.vblog.vdo.ResultCode;
import top.lpepsi.vblog.vdo.TagDO;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: v-blog
 * @description: 博客
 * @author: 林北
 * @create: 2020-02-18 11:26
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogServiceImpl implements BlogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private LikeServiceImpl likeService;

    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private EsBlogServiceImpl esBlogService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Response<Object> blogs(Integer currentPage) {
        if(null == currentPage){
            LOGGER.error("currentPage为空");
            return Response.failure("currentPage为空");
        }
        if(!redisUtil.hasKey(RedisKeyConstant.BLOG)){
            synchronized (this){
                saveBlogs2Redis();
            }
        }
        return Response.success(redisService.pagination(Long.valueOf(currentPage)));
    }

    @Override
    public Response findBlog(Integer articleId) {
        if (null == articleId){
            return Response.failure("articleId为空");
        }
        if(!redisUtil.hasKey(RedisKeyConstant.BLOG)){
            synchronized (this){
                saveBlogs2Redis();
            }
        }
        if(!redisUtil.hashHasKey(RedisKeyConstant.BLOG,String.valueOf(articleId))){
            synchronized (this){
                Detail detail = blogMapper.findBlog(articleId);
                redisService.saveBlog2Redis(detail);
            }
        }
        Detail detail = redisService.getBlogFromRedis(String.valueOf(articleId));
        detail.setLikeNum(likeService.getLikedCountByIdFromRedis(articleId));
        detail.setArticleContent(MarkDownToHtmlUtil.mdToHtml(detail.getArticleContent()));
        return Response.success(detail);
    }

    @Override
    public Response<String> saveBlog2DB(Edit edit) {
        if (null == edit){
            LOGGER.error("edit为空");
        }
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            blogMapper.saveBlog2DB(edit);
            Integer articleId = edit.getId();
            blogMapper.saveContent2DB(edit);
            blogMapper.saveImage2DB(edit);
            for (String tag : edit.getTagArray()) {
                TagDO tagDO = new TagDO(tag);
                int tagId = tagService.saveTag2DB(tagDO,edit);
                tagService.tagToArticle(tagId, articleId);
            }
            saveBlog2Redis(blogMapper.findBlog(articleId));
            return Response.success("发布成功");
        } catch (Exception e) {
           LOGGER.error("保存博客出错："+e.getMessage());
           return Response.failure("发布失败");
        }finally {
            lock.unlock();
        }
    }

    @Override
    public Response<List<Detail>> getRecentBlogs() {
        return Response.success( redisService.getRecentBlogsFromRedis());
    }

    @Override
    public Response<List<Detail>> getMostViewBlog() {
        return Response.success(redisService.getMostViewBlog());
    }

    @Override
    public void saveBlogView2DB() {
        Set<ZSetOperations.TypedTuple<String>> set = redisUtil.zSetReverseRangeWithScores(RedisKeyConstant.VIEW, 0L, -1L);
        set.forEach(tuple ->{
            String articleId = tuple.getValue();
            double num = tuple.getScore();
            blogMapper.saveBlogView(articleId, num);
        });
        redisUtil.delete(RedisKeyConstant.BLOG);
        redisUtil.delete(RedisKeyConstant.BLOG_LIST);
        saveBlogs2Redis();
    }


    private void saveBlogs2Redis(){
        if (!redisUtil.hasKey(RedisKeyConstant.BLOG)){
            List<Detail> detailBlogs = blogMapper.detailBlogs();
            esBlogService.importAll(detailBlogs);
            redisService.firstSaveBlogs2Redis(detailBlogs);
        }
    }

    private void saveBlog2Redis(Detail detail){
        esBlogService.create(detail);
        redisService.saveBlog2Redis(detail);
    }
}
