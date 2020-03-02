package top.lpepsi.vblog.service.tag.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lpepsi.vblog.constant.RedisKeyConstant;
import top.lpepsi.vblog.dao.BlogMapper;
import top.lpepsi.vblog.dao.TagMapper;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.cache.impl.RedisServiceImpl;
import top.lpepsi.vblog.service.tag.TagService;
import top.lpepsi.vblog.utils.RedisUtil;
import top.lpepsi.vblog.vdo.TagDO;

import java.util.List;
import java.util.Map;

/**
 * @program: v-blog
 * @description: 标签
 * @author: 林北
 * @create: 2020-02-19 10:17
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class TagServiceImpl implements TagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void tagToArticle(Integer tagId, Integer articleId) {
        tagMapper.tagToArticle(tagId, articleId);
    }

    @Override
    public int saveTag2DB(TagDO tagDO, Edit edit) {
        try {
            int i = tagMapper.saveTag2DB(tagDO);
            if (i == 1){
                tagDO.setTagNumber(1);
                redisUtil.listPushRight(RedisKeyConstant.TAGS, tagDO);
            }
            if (i == 2){
                List<Detail> list = (List<Detail>) redisUtil.hashGet(RedisKeyConstant.TAG_BLOG, tagDO.getTagName());
                list.add(edit);
                redisUtil.hashPut(RedisKeyConstant.TAG_BLOG, tagDO.getTagName(), list);
            }
            return tagDO.getId();
        } catch (Exception e) {
            LOGGER.error("标签保存出错: "+e.getMessage());
        }
        return 0;
    }

    @Override
    public Response getTags() {
        try {
            if (!redisUtil.hasKey(RedisKeyConstant.TAGS)){
                    redisUtil.listLeftPushAll(RedisKeyConstant.TAGS, tagMapper.getTags());
            }
            List<TagDO> list = redisUtil.listGetAll(RedisKeyConstant.TAGS);
            if (null != list){
                list.forEach(tagDO -> {
                    List<Detail> blogByTagName = blogMapper.findBlogByTagName(tagDO.getTagName());
                    redisUtil.hashPut(RedisKeyConstant.TAG_BLOG, tagDO.getTagName(), blogByTagName);
                });
            }
            return Response.success(list);
        } catch (Exception e) {
            LOGGER.error("getTags发生异常："+e.getMessage());
        }

        return Response.failure("获取标签失败");
    }

    @Override
    public Response getBlogByTag() {
        Map<String,List<Detail>> map = redisUtil.hashGetEntry(RedisKeyConstant.TAG_BLOG);
        return Response.success(map);
    }
}
