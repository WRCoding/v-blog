package top.lpepsi.vblog.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lpepsi.vblog.job.QuartzJob;


/**
 * @program: v-blog
 * @description: 定时任务配置类
 * @author: 林北
 * @create: 2020-02-28 15:38
 **/
@Configuration
public class QuartzConfig {
    private static final String QUARTZ_JOB = "job";
    private static final String QUARTZ_TRIGGER = "trigger";

    @Bean
    public JobDetail likeJobDetail(){
        return JobBuilder.newJob(QuartzJob.class).withIdentity(QUARTZ_JOB).storeDurably().build();
    }

    @Bean
    public Trigger likeCountJobTrigger(){
        //  -- 0 0 12 1/1 * ? *
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 12 1/1 * ? *");
        return TriggerBuilder.newTrigger().forJob(likeJobDetail()).withIdentity(QUARTZ_TRIGGER).withSchedule(cronScheduleBuilder).build();
    }
}
