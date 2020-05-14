package top.lpepsi.vblog.dao;

import org.apache.ibatis.annotations.Mapper;
import top.lpepsi.vblog.dto.Detail;

import java.util.List;

/**
 * @program: v-blog
 * @description: 归档
 * @author: 林北
 * @create: 2020-04-21 15:36
 **/
@Mapper
public interface ArchiveMapper {
    /**
     * @Description: 获得所有文章的年份集合
     * @Param: []
     * @return: java.util.List<java.lang.Integer>
     * @Author: 林北
     * @Date: 2020-04-21
     */
    List<Integer> getYear();

    /**
     * @Description: 获得对于年份的所有文章
     * @Param: [year]
     * @return: java.util.List<top.lpepsi.vblog.vdo.ArticleDO>
     * @Author: 林北
     * @Date: 2020-04-21
     */
    List<Detail> getBlogsByYear(Integer year);
}
