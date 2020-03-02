package top.lpepsi.vblog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lpepsi.vblog.vdo.TagDO;

import java.util.List;

/**
 * @program: v-blog
 * @description: 标签Dao
 * @author: 林北
 * @create: 2020-02-19 09:30
 **/
@Mapper
public interface TagMapper {
    
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
    int saveTag2DB(TagDO tagDO);

    /**
    * @Description: 获取所有标签
    * @Param: []
    * @return: java.util.List<top.lpepsi.vblog.vdo.TagDO>
    * @Author: 林北
    * @Date: 2020-02-21
    */
    List<TagDO> getTags();
}
