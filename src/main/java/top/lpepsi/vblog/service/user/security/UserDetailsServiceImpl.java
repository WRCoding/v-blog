package top.lpepsi.vblog.service.user.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import top.lpepsi.vblog.dao.UserMapper;
import top.lpepsi.vblog.jwt.JwtUserDetails;
import top.lpepsi.vblog.vdo.UserDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: v-blog
 * @description: 实现UserDetailsService
 * @author: 林北
 * @create: 2020-02-16 10:08
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username){
        UserDO user = userMapper.findUserByuserName(username);
        if (user == null){
            throw new BadCredentialsException("邮箱不存在");
        }
        if (user.getStatus() == 0){
            throw new DisabledException("账号未激活");
        }
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(user.getRole()));
        return new JwtUserDetails(user.getUserName(),user.getPassword(),list);
    }
}
