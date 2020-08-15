package com.scmp.gateway.service.fallback;

import com.scmp.domain.ResultVO;
import com.scmp.domain.User;
import com.scmp.gateway.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements UserService {

    @Override
    public ResultVO<User> verifyToken(String token) {
        return new ResultVO<>(404, "调用远程接口失败或超时", null);
    }

    @Override
    public ResultVO<User> getUserById(int userId) {
        return new ResultVO<>(404, "调用远程接口失败或超时", null);
    }
}
