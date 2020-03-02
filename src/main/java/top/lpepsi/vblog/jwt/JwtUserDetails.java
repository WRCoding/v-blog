package top.lpepsi.vblog.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.lpepsi.vblog.vdo.UserDO;

import java.util.Collection;

/**
 * @program: v-blog
 * @description: 继承UserDO,实现UserDetails
 * @author: 林北
 * @create: 2020-02-16 10:05
 **/
public class JwtUserDetails implements UserDetails {
    private Integer id;
    private String username;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public JwtUserDetails(String username,String password,Collection<? extends GrantedAuthority> authorities) {

        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
