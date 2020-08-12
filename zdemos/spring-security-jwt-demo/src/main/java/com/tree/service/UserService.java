package com.tree.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tree.mapper.UserMapper;
import com.tree.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Coconut Tree
 * 2020/8/12 0:15
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User loadUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }
}
