package top.lpepsi.vblog.service.like.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lpepsi.vblog.constant.RedisKeyConstant;
import top.lpepsi.vblog.dao.LikeMapper;
import top.lpepsi.vblog.service.like.LikeService;
import top.lpepsi.vblog.utils.RedisUtil;
import top.lpepsi.vblog.vdo.LikeCount;
import top.lpepsi.vblog.vdo.LikeDO;

import java.util.*;

/**
 * @program: v-blog
 * @description: 点赞实现类
 * @author: 林北
 * @create: 2020-02-25 10:23
 **/
@Service
public class LikeServiceImpl implements LikeService {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LikeMapper likeMapper;


    @Override
    public Integer getLikeStatus(String username, Integer articleId) {
        String key = RedisKeyConstant.getLikeKey(username,articleId);
        if (redisUtil.hashHasKey(RedisKeyConstant.USER_LIKE, key)){
            return (Integer) redisUtil.hashGet(RedisKeyConstant.USER_LIKE, key);
        }else {
            Integer likeStatus = getLikeStatusByUserNameAndArticleId(username,articleId) == null?0:likeMapper.getLikeStatus(username, articleId).getLikeStatus();
            redisUtil.hashPut(RedisKeyConstant.USER_LIKE, key, likeStatus);
            return likeStatus;
        }
    }

    @Override
    public LikeDO getLikeStatusByUserNameAndArticleId(String username, Integer articleId) {
        return likeMapper.getLikeStatus(username, articleId);
    }

    @Override
    public void Liked2Redis(String username, Integer articleId) {
        String key = RedisKeyConstant.getLikeKey(username,articleId);
        redisUtil.hashPut(RedisKeyConstant.USER_LIKE, key,1);
        redisUtil.hashIncrement(RedisKeyConstant.LIKE_COUNT,  String.valueOf(articleId), 1);
    }

    @Override
    public void unLiked2Redis(String username, Integer articleId) {
        String key = RedisKeyConstant.getLikeKey(username,articleId);
        redisUtil.hashPut(RedisKeyConstant.USER_LIKE, key,0);
        redisUtil.hashIncrement(RedisKeyConstant.LIKE_COUNT, String.valueOf(articleId), -1);
    }

    @Override
    public Integer getLikedCountByIdFromRedis(Integer articleId) {
        if (redisUtil.hashHasKey(RedisKeyConstant.LIKE_COUNT, String.valueOf(articleId))){
            return (Integer) redisUtil.hashGet(RedisKeyConstant.LIKE_COUNT, String.valueOf(articleId));
        }else {
            int count = likeMapper.getLikedCountByIdFromDB(articleId);
            redisUtil.hashPut(RedisKeyConstant.LIKE_COUNT, String.valueOf(articleId), count);
            return count;
        }
    }

    @Override
    public void saveLikedCount2DB() {
        List<LikeCount> likeCountList = getLikedCountFromRedis();
        likeCountList.forEach(likeCount -> likeMapper.saveLikedCount2DB(likeCount));
    }


    private List<LikeCount> getLikedCountFromRedis() {
        HashMap<String,Integer> hashMap = redisUtil.hashGetEntry(RedisKeyConstant.LIKE_COUNT);
        List<LikeCount> likeList = new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Integer> entry = iterator.next();
            String articleId = entry.getKey();
            Integer num = entry.getValue();
            LikeCount likeCount = new LikeCount(Integer.parseInt(articleId), num);
            likeList.add(likeCount);
        }
        return likeList;
    }

    @Override
    public void saveUserLiked2DB() {
        List<LikeDO> list = getUserLikedStatusFormRedis();
        list.forEach(like->{
            if (likeMapper.updateUserLikedStatus2DB(like) == 0){
                likeMapper.saveUserLikedStatus2DB(like);
            }
        });
    }

    private List<LikeDO> getUserLikedStatusFormRedis(){
        HashMap<String,Integer> hashMap = redisUtil.hashGetEntry(RedisKeyConstant.USER_LIKE);
        List<LikeDO> likeList = new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Integer> entry = iterator.next();
            String username = entry.getKey().split(":")[0];
            Integer key = Integer.valueOf(entry.getKey().split(":")[1]);
            Integer likeStatus = entry.getValue();
            LikeDO likeDO = new LikeDO(username,key,likeStatus);
            likeList.add(likeDO);
        }
        return likeList;
    }
}
