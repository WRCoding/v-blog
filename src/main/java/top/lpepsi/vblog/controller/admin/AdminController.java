package top.lpepsi.vblog.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.lpepsi.vblog.controller.blog.BlogController;
import top.lpepsi.vblog.dto.Edit;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.admin.AdminService;

/**
 * @program: v-blog
 * @description: 管理接口
 * @author: 林北
 * @create: 2020-03-29 10:21
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {


    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @GetMapping("/blogManager")
    public Response blogManager(@RequestParam("username")String username,@RequestParam("pageNum")Integer pageNum){
        return adminService.blogManager(username,pageNum);
    }

    @GetMapping("/personalData")
    public Response personalData(@RequestParam("username")String username){
        return adminService.personalData(username);
    }

    @GetMapping("/edit")
    public Response edit(@RequestParam("articleId")Integer articleId){
        return adminService.editByArticleId(articleId);
    }

    @PutMapping("/edit")
    public Response editBlog(@RequestBody Edit edit){
        LOGGER.info("更新文章： "+edit);
        return adminService.updateBlog(edit);
    }

}
