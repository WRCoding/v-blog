package top.lpepsi.vblog.service.archive.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lpepsi.vblog.constant.RedisKeyConstant;
import top.lpepsi.vblog.dao.ArchiveMapper;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.archive.ArchiveService;
import top.lpepsi.vblog.utils.RedisUtil;
import top.lpepsi.vblog.vdo.ArticleDO;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: v-blog
 * @description: 归档
 * @author: 林北
 * @create: 2020-04-21 15:42
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArchiveMapper archiveMapper;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Response getYear() {
        try {
            if (!redisUtil.hasKey(RedisKeyConstant.ARCHIVE)){
                redisUtil.listLeftPushAll(RedisKeyConstant.ARCHIVE, archiveMapper.getYear());
            }
            List<Integer> list = redisUtil.listGetAll(RedisKeyConstant.ARCHIVE);
            list.forEach(year -> {
                List<Detail> blogsByYear = archiveMapper.getBlogsByYear(year);
                redisUtil.hashPut(RedisKeyConstant.ARCHIVE_BLOG, String.valueOf(year), blogsByYear);
            });
            return Response.success(list);
        }catch (Exception e){
            log.error("getYear发生异常： "+e.getMessage());
        }
        return Response.failure("获取归档失败");
    }

    @Override
    public Response getBlogsByYear() {
        Map<String,List<Detail>> map = redisUtil.hashGetEntry(RedisKeyConstant.ARCHIVE_BLOG);
        return Response.success(map);
    }
}
