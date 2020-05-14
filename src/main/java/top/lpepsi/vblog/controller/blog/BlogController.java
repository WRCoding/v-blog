package top.lpepsi.vblog.controller.blog;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.lpepsi.vblog.controller.loginandreg.LoginAndRegController;
import top.lpepsi.vblog.dao.BlogMapper;
import top.lpepsi.vblog.dto.Blog;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.admin.AdminService;
import top.lpepsi.vblog.service.blog.impl.BlogServiceImpl;
import top.lpepsi.vblog.vdo.ArticleDO;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: v-blog
 * @description: 博客接口
 * @author: 林北
 * @create: 2020-02-18 09:27
 **/
@Controller
public class BlogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private BlogServiceImpl blogService;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private AdminService adminService;


    @GetMapping("/blogs")
    @ResponseBody
    public Response blogs(@RequestParam(value = "currentPage",required = false)Integer currentPage){
        if (currentPage < 1){
            return Response.failure("分页参数错误");
        }
        return blogService.blogs(currentPage);
    }

    @GetMapping("/blogs/{key}")
    @ResponseBody
    public Response<List<Detail>> getRecentBlogs(@PathVariable("key")String key){
        return blogService.getRecentBlogs();
    }

    @PostMapping("/article")
    @ResponseBody
    public Response createBlog(@RequestBody Edit edit){
        return blogService.saveBlog2DB(edit);
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public Response article(@PathVariable("id")Integer articleId){
        return Response.success(blogService.findBlog(articleId));
    }

    @GetMapping("/mostview")
    @ResponseBody
    public Response<List<Detail>> saveBlogs2Redis(){
        return blogService.getMostViewBlog();
    }
}
