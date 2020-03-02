package top.lpepsi.vblog.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.lpepsi.vblog.dto.Detail;

import java.util.List;


/**
 * @program: v-blog
 * @description: 文章搜索ES操作类
 * @author: 林北
 * @create: 2020-03-01 15:05
 **/
public interface BlogSearchRepository extends ElasticsearchRepository<Detail,Integer> {
    /**
    * @Description: 搜索查询，根据作者，标题，内容搜索
    * @Param: [author, articleTtile, articleContent]
    * @return: java.util.List<top.lpepsi.vblog.vdo.document.ArticleDocument>
    * @Author: 林北
    * @Date: 2020-03-01
    */
    List<Detail> findByAuthorOrArticleTitleOrArticleContent(String author, String articleTtile, String articleContent);
}
