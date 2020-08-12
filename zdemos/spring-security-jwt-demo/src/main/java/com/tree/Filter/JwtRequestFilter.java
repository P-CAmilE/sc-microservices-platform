package com.tree.Filter;

import com.tree.service.UserDetailsServiceImpl;
import com.tree.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Coconut Tree
 * 2020/8/11 23:54
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String jwt = null;
        String username = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // jwt
            jwt = authorizationHeader.substring(7);
            // 用户请求header 中包含的 jwt 中包含的用户名，对应subject
            username = jwtUtils.extractUsername(jwt);
        }
        // jwt 中的userName 确实数据库中有存在 && 当前上下文中还没有对该用户的认证
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 可以拿到用户 username，password，authorities
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 用户名相同(其实已经满足) && jwt 没过期
            if (jwtUtils.validateToken(jwt, userDetails)) {
                // 下面是认证通过后 spring security 自动完成的，自己要做的原因是前提是 jwt validated
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                // 这里的 Details 包含 remoteAddress sessionId
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetails(httpServletRequest));
                // 这样就表示认证过了
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // 如果 jwt 认证成功，则 spring security 认证成功，否则将会在其他的 filter 都失败，所以就 认证失败
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
