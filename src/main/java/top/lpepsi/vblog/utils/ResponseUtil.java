package top.lpepsi.vblog.utils;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: v-blog
 * @description: 响应工具类
 * @author: 林北
 * @create: 2020-02-17 10:36
 **/
public class ResponseUtil {

    public static HttpServletResponse set(HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        return response;
    }
}
