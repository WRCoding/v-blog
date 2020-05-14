package top.lpepsi.vblog.utils;

import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import top.lpepsi.vblog.constant.RedisKeyConstant;

import javax.annotation.PostConstruct;
import javax.swing.text.TabableView;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: v-blog
 * @description: Redis工具类
 * @author: 林北
 * @create: 2020-02-18 15:31
 **/
@Component
public class RedisUtil<HK,T> {
    @Autowired
    private  RedisTemplate redisTemplate;

    private ZSetOperations<String,T> zSetOperations;
    private HashOperations<String,HK ,T > hashOperations ;
    private ListOperations<String,T> listOperations;
    private SetOperations<String,T> setOperations;
    private ValueOperations<String,T> valueOperations;

    /**
     * 只执行一次，在构造函数之后执行
     */
    @PostConstruct
    public void init(){
        zSetOperations = redisTemplate.opsForZSet();
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
        setOperations = redisTemplate.opsForSet();
        valueOperations = redisTemplate.opsForValue();
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }
    public boolean expirse(String key,Long timeout,TimeUnit timeUnit){
        return redisTemplate.expire(key,timeout,timeUnit);
    }
    /**
    * @Description: 以下是Hash类型
    * @Param: [key, hashKey, value]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-22
    */
    public void hashPut(String key,HK hashKey,T value){
        hashOperations.put(key,hashKey,value);
    }
    public T hashGet(String key,HK hashKey){
        return hashOperations.get(key, hashKey);
    }
    public void hashDelete(String key,HK hashKey){
        hashOperations.delete(key,hashKey);
    }
    public boolean hashHasKey(String key,HK hashKey){
        return hashOperations.hasKey(key, hashKey);
    }
    public HashMap<HK,T> hashGetEntry(String key){
        return (HashMap<HK, T>) hashOperations.entries(key);
    }
    public void hashIncrement(String key,HK hashKey,long num){
        hashOperations.increment(key,hashKey,num);
    }
    /**
    * @Description: 以下是List类型
    * @Param: [key, collection]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-22
    */
    public void listRightPushAll(String key, Collection<T> collection){
        listOperations.rightPushAll(key,collection);
    }
    public void listPushRight(String key,T value){
        listOperations.rightPush(key, value);
    }
    public void listLeftPushAll(String key,Collection<T>collection){
        listOperations.leftPushAll(key,collection);
    }
    public void listPushLeft(String key,T value){
        listOperations.leftPush(key,value);
    }
    public Long size(String key){
        return listOperations.size(key);
    }
    public List<T> listGetAll(String key){
        return listOperations.range(key, 0, size(key));
    }
    public List<T> listGetRange(String key,Long start,Long end){
        return listOperations.range(key, start, end);
    }
    public Long listRemove(String key, T value){
        return listOperations.remove(key,0,value);
    }

    /**
    * @Description: 以下是Value
    * @Param: [key, value]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-22
    */
    public void valuePut(String key,T value,long timeout, TimeUnit unit){
        valueOperations.set(key,value,timeout,unit);
    }
    public T valueGet(String key){
        return  valueOperations.get(key);
    }
    /**
    * @Description: 以下是zSet
    * @Param: [key, value, score]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-22
    */
    public void zSetPut(String key,T value,Double score){
        zSetOperations.add(key,value,score);
    }
    public void zSetDelete(String key,T value){
        zSetOperations.remove(key,value);
    }
    public Double zSetGetScore(String key,T value){
        return zSetOperations.score(key,value);
    }
    public Set<T> zSetReverseRange(String key ,Long start,Long end){
        return zSetOperations.reverseRange(key,start,end);
    }
    public Set<ZSetOperations.TypedTuple<T>> zSetReverseRangeWithScores(String key ,Long start,Long end){
        return zSetOperations.reverseRangeWithScores(key,start,end);
    }
    public void zSetAdd(String key,T value,Double num){
        zSetOperations.incrementScore(key,value,num);
    }
}
