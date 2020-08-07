package com.scmp.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Coconut Tree
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @GetMapping("/checkUserTime")
    public boolean checkUserTime(@RequestParam("department") String department,
                                 @RequestParam("username") String username,
                                 @RequestParam("nowTime") String nowTime) throws Exception {
        throw new Exception("故意的");
//        return true;
    }

    @GetMapping("/getEmailByUsernameAndDepartment")
    String getEmailByUsernameAndDePartment(@RequestParam("department") String department,
                                           @RequestParam("username") String username) {
        return "codefish@whu.edu.cn";
    }
}
