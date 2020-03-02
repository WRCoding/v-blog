package top.lpepsi.vblog.service.es;

import org.springframework.data.domain.Page;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.vdo.ArticleDO;

import java.util.List;


/**
 * @program: v-blog
 * @description: 文章搜索管理Service
 * @author: 林北
 * @create: 2020-03-01 15:27
 **/
public interface EsBlogService {

    /**
    * @Description: 从数据库导入所有文章进ES
    * @Param: []
    * @return: int
    * @Author: 林北
    * @Date: 2020-03-01
    */
    int importAll(List<Detail>list);

    /**
    * @Description: 将新增的文章添加到ES中
    * @Param: [articleDO]
    * @return: top.lpepsi.vblog.vdo.ArticleDO
    * @Author: 林北
    * @Date: 2020-03-01
    */
    void create(Detail Detail);

    /**
    * @Description: 根据关键字搜索
    * @Param: [author, pageNum, pageSize]
    * @return: java.util.List<top.lpepsi.vblog.vdo.ArticleDO>
    * @Author: 林北
    * @Date: 2020-03-01
    */
    List<Detail> searech(String keyWord);
}
