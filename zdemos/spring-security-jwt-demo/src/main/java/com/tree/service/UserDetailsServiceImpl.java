package com.tree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Coconut Tree
 * 2020/8/11 23:22
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 测试
//        List<GrantedAuthority> list = Arrays.asList(new SimpleGrantedAuthority("user"));
//        User user = new User("foo", "foo", list);
        com.tree.models.User user1 = userService.loadUserByUsername(s);
        String[] authorities = user1.getAuthorities().split(",");
        List<GrantedAuthority> authorities1 = new ArrayList<>();
        for (String auth: authorities) {
            authorities1.add(new SimpleGrantedAuthority(auth));
        }
        return new User(user1.getUsername(), user1.getPassword(), authorities1);
    }
}
