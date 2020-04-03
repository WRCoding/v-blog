package top.lpepsi.vblog.service.tag;

import org.apache.ibatis.annotations.Param;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.vdo.TagDO;

import java.util.List;

/**
 * @program: v-blog
 * @description: 标签
 * @author: 林北
 * @create: 2020-02-19 10:16
 **/
public interface TagService {

    /**
     * @Description: 保存标签到对应的文章
     * @Param: [sortId, articleId]
     * @return: void
     * @Author: 林北
     * @Date: 2020-02-19
     */
    void tagToArticle(@Param("tagId")Integer tagId, @Param("articleId")Integer articleId);

    /**
     * @Description: 更新标签下的文章数
     * @Param: [sortId, isDelete]
     * @return: int
     * @Author: 林北
     * @Date: 2020-02-19
     */
    int saveTag2DB(TagDO tagDO, Edit edit);
    
    /**
    * @Description: 获取所有标签
    * @Param: []
    * @return: java.util.List<top.lpepsi.vblog.vdo.TagDO>
    * @Author: 林北
    * @Date: 2020-02-21
    */
    Response getTags();

    /**
    * @Description: 获取所有标签及其对应的文章列表
    * @Param: []
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-02-23
    */
    Response getBlogByTag();

    /**
    * @Description: 获取指定标签下的博客
    * @Param: []
    * @return: top.lpepsi.vblog.dto.Response
    * @Author: 林北
    * @Date: 2020-03-22
    */
    Response getBlogWithTag(String key);
}
