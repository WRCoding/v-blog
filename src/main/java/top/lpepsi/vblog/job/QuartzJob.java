package top.lpepsi.vblog.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import top.lpepsi.vblog.service.admin.impl.AdminServiceImpl;
import top.lpepsi.vblog.service.blog.impl.BlogServiceImpl;
import top.lpepsi.vblog.service.like.impl.LikeServiceImpl;
import top.lpepsi.vblog.service.user.impl.UserServiceImpl;

import java.util.Arrays;

/**
 * @program: v-blog
 * @description: 定时任务
 * @author: 林北
 * @create: 2020-02-28 15:44
 **/
public class QuartzJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJob.class);

    @Autowired
    private BlogServiceImpl blogService;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private LikeServiceImpl likeService;
    @Autowired
    private AdminServiceImpl adminService;

    @Override
    protected void executeInternal(JobExecutionContext context){
        try {
            blogService.saveBlogView2DB();
            userService.saveUserView2DB();
            likeService.saveUserLiked2DB();
            likeService.saveLikedCount2DB();
            adminService.updateRecord();
            adminService.updateRecordView();
        } catch (Exception e) {
            LOGGER.error("定时任务发生异常： "+ Arrays.toString(e.getStackTrace()));
        }
    }
}
