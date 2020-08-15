package com.scmp.gateway.filter;

import com.scmp.domain.ResultVO;
import com.scmp.domain.User;
import com.scmp.gateway.service.UserService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private UserService userService;

    private static final Set<String> ignorePaths = new HashSet<>();

    static {
        ignorePaths.add("/user/login");
        ignorePaths.add("/user/register");
        ignorePaths.add("/file/download");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();
        String URI = req.getURI().getPath();
        // exclude login URI
        if(ignorePaths.contains(URI))
            return chain.filter(exchange);

        String token = req.getHeaders().getFirst("token");
        if(token == null || "".equals(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        ResultVO<User> verifyTokenResult = userService.verifyToken(token);
        // exclude exceptions in verify token
        if(verifyTokenResult.getCode() != 200) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        User user = verifyTokenResult.getData();
//        System.out.println(user.getUserId());
        
        ResultVO<User> getUserResult = userService.getUserById(user.getUserId());
        // exclude exceptions in get user by id
        if(getUserResult.getCode() != 200) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        User realUser = getUserResult.getData();
        // verify information in token
        if(!user.getUserAccount().equals(realUser.getUserAccount()) ||
           user.getUserType() != realUser.getUserType()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        req.mutate().headers(httpHeaders -> {
            httpHeaders.add("userId",String.valueOf(user.getUserId()));
            httpHeaders.add("userAccount",user.getUserAccount());
            httpHeaders.add("userType",String.valueOf(user.getUserType()));
        });
        ServerWebExchange exchange1 = exchange.mutate().request(req).build();
        return chain.filter(exchange1);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
