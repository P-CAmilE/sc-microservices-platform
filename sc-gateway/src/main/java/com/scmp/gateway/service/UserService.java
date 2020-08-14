package com.scmp.gateway.service;

import com.scmp.domain.ResultVO;
import com.scmp.domain.User;
import com.scmp.gateway.service.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service", fallback = UserServiceFallback.class)
public interface UserService {

    @GetMapping("/user/VerifyToken")
    ResultVO<User> verifyToken(@RequestParam("token") String token);

    @GetMapping("/user/getById")
    ResultVO<User> getUserById(@RequestParam("userId") int userId);

}
