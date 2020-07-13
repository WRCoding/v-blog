package top.lpepsi.vblog.vdo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @program: v-blog
 * @description: 用户DO
 * @author: 林北
 * @create: 2020-02-15 11:54
 **/
@Data
@NoArgsConstructor
public class UserDO implements Serializable {
    private static final long serialVersionUID = 9115484650221598201L;

    private Integer id;
    private String email;
    private String password;
    private String userName;
    private String role;
    private Integer status;
    private Date createBy;



    public UserDO(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
}
