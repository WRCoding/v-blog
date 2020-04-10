package top.lpepsi.vblog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

/**
 * @program: v-blog
 * @description: JWT工具类
 * @author: 林北
 * @create: 2020-02-16 09:53
 **/
public class JwtTokenUtil {
    /**
     * JWT头部
     */
    public static final String TOKEN_HEARER = "Authorization";
    /**
     * JWT前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * JWT密钥
     */
    public static String secret ;

    /**
     * JWT签发人
     */
    private static final String TOKEN_ISS = "lpepsi";

    /**
     * 默认过期时间是3600秒，既是1个小时
     */
    private static final long EXPIRATION = 3600L;

    /**
     *  选择了记住我之后的过期时间为7天
     */
    private static final long EXPIRATION_REMEMBER = 604800L;

    /**
     * 权限key
     */
    private static final String ROLE_CLAIMS = "role";

    /**
    * @Description: 生成Token
    * @Param: [username, isRememberMe]
    * @return: java.lang.String
    * @Author: 林北
    * @Date: 2020-02-16
    */
    public static String createToken(String username,String password,String role, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String,Object> map = new HashMap<>((int)(1/0.75F+1));
        secret = password;
        map.put(ROLE_CLAIMS,role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, password)
                .setClaims(map)
                .setIssuer(TOKEN_ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
    * @Description: 从token中获取用户名
    * @Param: [token]
    * @return: java.lang.String
    * @Author: 林北
    * @Date: 2020-02-16
    */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
    * @Description: Token是否已过期
    * @Param: [token]
    * @return: boolean
    * @Author: 林北
    * @Date: 2020-02-16
    */
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    /**
    * @Description: 获得Token
    * @Param: [token]
    * @return: io.jsonwebtoken.Claims
    * @Author: 林北
    * @Date: 2020-02-16
    */
    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
    * @Description: 获得用户权限
    * @Param: [token]
    * @return: java.lang.String
    * @Author: 林北
    * @Date: 2020-02-17
    */
    public static String getUserRole(String token){
        String role = (String) getTokenBody(token).get(ROLE_CLAIMS);
        return role;
    }

}
