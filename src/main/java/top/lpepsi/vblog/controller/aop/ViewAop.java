package top.lpepsi.vblog.controller.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import top.lpepsi.vblog.constant.RedisKeyConstant;
import top.lpepsi.vblog.dto.Detail;
import top.lpepsi.vblog.utils.JwtTokenUtil;
import top.lpepsi.vblog.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: v-blog
 * @description: 访问量Aop
 * @author: 林北
 * @create: 2020-02-25 09:08
 **/
@Component
@Aspect
public class ViewAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewAop.class);

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("execution(* top.lpepsi.vblog.controller.blog.BlogController.article(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void setView(JoinPoint point){
        try {
            Object[] args = point.getArgs();
            Integer id = (Integer) args[0];
            if (redisUtil.hashHasKey(RedisKeyConstant.BLOG, String.valueOf(id))){
               String username = ((Detail) redisUtil.hashGet(RedisKeyConstant.BLOG, String.valueOf(id))).getAuthor();
                redisUtil.zSetAdd(RedisKeyConstant.VIEW,  String.valueOf(id), 1D);
                redisUtil.zSetAdd(RedisKeyConstant.USER_VIEW, username, 1D);
            }
        } catch (Exception e) {
            LOGGER.error("top.lpepsi.vblog.controller.aop.ViewAop.setView发生异常："+e.getStackTrace());
        }
    }
}
