package top.lpepsi.vblog.jwt;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.utils.ResponseUtil;
import top.lpepsi.vblog.vdo.ResultCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: v-blog
 * @description: 认证失败处理类
 * @author: 林北
 * @create: 2020-02-17 11:03
 **/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response = ResponseUtil.set(response);
        String json = JSON.toJSONString(Response.customize(ResultCode.ACCESS_DENY, "会话超时，请重新登录"));
        LOGGER.info("json: "+json);
        response.getWriter().write(json);
    }
}
