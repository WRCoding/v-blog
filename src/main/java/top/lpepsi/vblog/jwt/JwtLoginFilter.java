package top.lpepsi.vblog.jwt;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.dto.User;
import top.lpepsi.vblog.utils.JwtTokenUtil;
import top.lpepsi.vblog.vdo.ResultCode;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @program: v-blog
 * @description: JWT用户登录过滤器
 * @author: 林北
 * @create: 2020-02-16 09:47
 **/
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter  {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtLoginFilter.class);

    private AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
    * @Description: 从请求中获取登录信息
    * @Param: [request, response]
    * @return: org.springframework.security.core.Authentication
    * @Author: 林北
    * @Date: 2020-02-16
    */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  {
        User user=null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),new ArrayList<>()));
        } catch (IOException e) {
            LOGGER.error("获取请求流失败：" + e.getMessage());
        } catch (AuthenticationException ae){
            LOGGER.error("登录错误："+ae.getMessage()+",user: "+user);
            unsuccessfulAuthentication(request,response,ae);
        }
        return null;
    }

    /**
    * @Description: 验证成功生成token返回
    * @Param: [request, response, chain, authResult]
    * @return: void
    * @Author: 林北
    * @Date: 2020-02-16
    */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtUserDetails userDetails = (JwtUserDetails) authResult.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        StringBuilder role = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            role.append(authority.getAuthority());
        }
        String token = JwtTokenUtil.createToken(userDetails.getUsername(), role.toString(),false);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        HashMap<String,String> info = new HashMap<>((int) (2/0.75F+1F));
        info.put("token", token);
        info.put("username", userDetails.getUsername());
        String json = JSON.toJSONString(Response.success(info));
        response.getWriter().write(json);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        try {
            response.setCharacterEncoding("utf-8");
            String errorMessage = failed.getMessage();
            if (failed instanceof BadCredentialsException){
                errorMessage = "密码错误";
            }
            response.setContentType("application/json; charset=utf-8");
            String json = JSON.toJSONString(Response.failure(errorMessage));
            response.getWriter().write(json);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
