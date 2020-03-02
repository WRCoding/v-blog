package top.lpepsi.vblog.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: v-blog
 * @description: 用户信息DTO
 * @author: 林北
 * @create: 2020-02-15 13:14
 **/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -8623105218395219920L;

    private String email;
    private String username;
    private String password;
}
