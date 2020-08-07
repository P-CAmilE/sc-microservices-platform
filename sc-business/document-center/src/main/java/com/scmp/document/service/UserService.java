package com.scmp.document.service;

import com.scmp.document.service.fallback.UserServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author Coconut Tree
 */
@FeignClient(value = "user-service",
        fallback = UserServiceFallBack.class)
public interface UserService {
    // 判断当前时间是否在用户的接收时间范围内
    @GetMapping("/user/checkUserTime")
    boolean checkUserTime(@RequestParam("department") String department,
                          @RequestParam("username") String username,
                          @RequestParam("nowTime") Date nowTime);

    // 根据 部门 和 用户名 查询 用户email
    @GetMapping("/user/getEmailByUsernameAndDepartment")
    String getEmailByUsernameAndDepartment(@RequestParam("department") String department,
                                           @RequestParam("username") String username);
}
