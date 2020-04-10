package top.lpepsi.vblog.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import top.lpepsi.vblog.utils.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @program: v-blog
 * @description: 鉴权
 * @author: 林北
 * @create: 2020-02-16 10:44
 **/
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEARER);
        try {
            if (token == null || JwtTokenUtil.isExpiration(token)){
                LOGGER.info("我还没登录");
                chain.doFilter(request,response);
            }else {
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
                super.doFilterInternal(request, response, chain);
            }
        } catch (ExpiredJwtException e) {
            LOGGER.error("我登录了，但token过期了");
            chain.doFilter(request,response);
        } catch (Exception e){
            chain.doFilter(request,response);
        }
    }
    /**
    * @Description: 获取用户信息并新建一个token
    * @Param: [tokenHeader]
    * @return: UsernamePasswordAuthenticationToken
    * @Author: 林北
    * @Date: 2020-02-16
    */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String userName = JwtTokenUtil.getUsername(token);
        String userRole = JwtTokenUtil.getUserRole(token);
        if (userName != null){
            return new UsernamePasswordAuthenticationToken(userName, null,
                    Collections.singleton(new SimpleGrantedAuthority(userRole)));
        }
        return null;
    }
}
