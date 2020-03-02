package top.lpepsi.vblog.service.es.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.lpepsi.vblog.es.BlogSearchRepository;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.service.es.EsBlogService;
import top.lpepsi.vblog.vdo.ArticleDO;

import java.util.Iterator;
import java.util.List;

/**
 * @program: v-blog
 * @description: 文章搜索管理Service实现类
 * @author: 林北
 * @create: 2020-03-01 15:37
 **/
@Service
public class EsBlogServiceImpl implements EsBlogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsBlogServiceImpl.class);

    @Autowired
    private BlogSearchRepository repository;

    @Override
    public int importAll(List<Detail> list) {
        Iterable<Detail> details = repository.saveAll(list);
        int result = 0;
        Iterator<Detail> iterator = details.iterator();
        while (iterator.hasNext()){
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void create(Detail detail) {
        repository.save(detail);
    }

    @Override
    public List<Detail> searech(String keyWord) {
//        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return repository.findByAuthorOrArticleTitleOrArticleContent(keyWord,keyWord,keyWord);
    }
}
