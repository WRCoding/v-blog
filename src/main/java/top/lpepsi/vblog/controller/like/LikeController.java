package top.lpepsi.vblog.controller.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.like.impl.LikeServiceImpl;

/**
 * @program: v-blog
 * @description: 点赞
 * @author: 林北
 * @create: 2020-02-25 11:05
 **/
@Controller
public class LikeController {

    @Autowired
    private LikeServiceImpl likeService;


    @GetMapping("/like/{articleId}/{username}")
    @ResponseBody
    public Response<Integer> getLikeStatus(@PathVariable("articleId")Integer articleId, @PathVariable("username")String username){
        Integer likeStatus = likeService.getLikeStatus(username, articleId);
        return Response.success(likeStatus);
    }

    @GetMapping("/likeStatus/{articleId}/{username}/{status}")
    @ResponseBody
    public Response likeStatus(@PathVariable("articleId")Integer articleId, @PathVariable("username")String username, @PathVariable("status")Integer status){
        System.out.println("STATUS: "+status);
        if (status == 1){
            likeService.unLiked2Redis(username, articleId);
        }else {
            likeService.Liked2Redis(username, articleId);
        }
        return Response.success(likeService.getLikedCountByIdFromRedis(articleId));
    }
}
