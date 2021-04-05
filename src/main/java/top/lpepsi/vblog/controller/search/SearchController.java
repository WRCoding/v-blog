package top.lpepsi.vblog.controller.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lpepsi.vblog.dao.BlogMapper;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.es.impl.EsBlogServiceImpl;

import java.util.List;

/**
 * @program: v-blog
 * @description: 搜索Controller
 * @author: 林北
 * @create: 2020-03-01 15:51
 **/
@Controller
public class SearchController {
    @Autowired
    private EsBlogServiceImpl esBlogService;

    @GetMapping("/search")
    @ResponseBody
    public Response search(@RequestParam(required = false) String keyWord,
                           @RequestParam(required = false,defaultValue = "0") Integer pageNum,
                           @RequestParam(required = false,defaultValue = "4") Integer pageSize){
        List<Detail> search = esBlogService.search(keyWord);
        return Response.success(search);
    }
}
