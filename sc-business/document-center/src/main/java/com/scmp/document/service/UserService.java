package com.scmp.document.service;

import com.scmp.document.service.fallback.UserServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Coconut Tree
 */
@FeignClient(value = "user-service",
        fallback = UserServiceFallBack.class)
public interface UserService {
    // 判断当前时间是否在用户的接收时间范围内
    @GetMapping("/user/checkUserTime")
    boolean checkUserTime(@RequestParam("userAccount") String userAccount);
}
