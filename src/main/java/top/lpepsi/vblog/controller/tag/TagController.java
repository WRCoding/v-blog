package top.lpepsi.vblog.controller.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.tag.impl.TagServiceImpl;

/**
 * @program: v-blog
 * @description: 标签
 * @author: 林北
 * @create: 2020-02-22 09:27
 **/
@Controller
public class TagController {

    @Autowired
    private TagServiceImpl tagService;

    @GetMapping("/tags")
    @ResponseBody
    public Response getTags(){
        return tagService.getTags();
    }
    @GetMapping("/tags/{key}")
    @ResponseBody
    public Response getBlogByTag(){
        return tagService.getBlogByTag();
    }
}
