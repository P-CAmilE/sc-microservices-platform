package com.tree.knife4j.controller;

import com.google.common.collect.Lists;
import com.tree.knife4j.common.Rest;
import com.tree.knife4j.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Coconut Tree
 * 2020/8/12 14:01
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation(value = "查询用户列表")
    @PostMapping(value = "/list")
    public Rest<List<User>> list(){
        Rest<List<User>> rest=new Rest<>();
        List<User> list= Lists.newArrayList(new User("user1","Java开发工程师","公司2")
                ,new User("user2","C开发工程师","公司1")
                ,new User("user3","JavaScript工程师","公司3")
                ,new User("user4","Ui工程师","公司4")
                ,new User("user5","总经理","公司1"));
        rest.setData(list);
        return rest;
    }

    @ApiOperation(value = "根据用户id查询用户详情")
    @GetMapping("/queryById")
    public Rest<User> queryById(@RequestParam(value = "id") String id){
        Rest<User> userRest=new Rest<>();
        userRest.setData(new User("user5","总经理","公司1"));

        return userRest;
    }
}
