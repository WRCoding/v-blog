package top.lpepsi.vblog.service.archive;

import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.vdo.ArticleDO;

import java.util.List;

/**
 * @program: v-blog
 * @description: 归档
 * @author: 林北
 * @create: 2020-04-21 15:36
 **/
public interface ArchiveService {


    /**
    * @Description: 获得所有文章的年份集合
    * @Param: []
    * @return: java.util.List<java.lang.Integer>
    * @Author: 林北
    * @Date: 2020-04-21
    */
    Response getYear();

    /**
    * @Description: 获得对于年份的所有文章
    * @Param: [year]
    * @return: java.util.List<top.lpepsi.vblog.vdo.ArticleDO>
    * @Author: 林北
    * @Date: 2020-04-21
    */
    Response  getBlogsByYear();
}
