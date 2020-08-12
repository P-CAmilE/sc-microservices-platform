package com.tree.controller;

import com.tree.models.RequestModel;
import com.tree.models.ResponseModel;
import com.tree.service.UserDetailsServiceImpl;
import com.tree.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Coconut Tree
 * 2020/8/11 23:35
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/getToken")
    public ResponseEntity<?> getToken(@RequestBody RequestModel requestModel) {
        // AuthenticationManager 会自动进行用户名密码认证，会使用到我们自定义的 UserDetailsServiceImpl
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(requestModel.getUsername(), requestModel.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // 认证通过，从数据库拿该用户
        final UserDetails userDetails = userDetailsService.loadUserByUsername(requestModel.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);
        // 返回 jwt
        return ResponseEntity.ok(new ResponseModel(jwt));
    }
}
