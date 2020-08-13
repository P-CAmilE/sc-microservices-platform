package com.scmp.gateway.service;

import com.scmp.domain.ResultVO;
import com.scmp.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-user")
//@FeignClient(value = "service-user", fallback = )
public interface UserService {

    @GetMapping("/user/VerifyToken")
    ResultVO<User> verifyToken(@RequestParam("token") String token);

    @GetMapping("/user/getById")
    ResultVO<User> getUserById(@RequestParam("userId") int userId);

}
