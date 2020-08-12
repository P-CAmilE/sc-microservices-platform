package com.tree.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Coconut Tree
 * 2020/8/11 22:50
 */
@Component
public class JwtUtils {
    /**
     * 对称加密密钥
     */
    private final String secret = "tree";

    // 从 token 中 提取信息
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 生成 token
    /**
     *
     * @param userDetails spring security 自定义的 MyUserDetailsService.loadUserByUsername 的返回结果
     * @return 带有用户名，用户权限的 jwt
     */
    public String generateToken(UserDetails userDetails) {
        // 可以在 map 里加任何想加的东西，不过大部分情况下，我们可能需要的是用户的权限
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .addClaims(claims).setSubject(subject)  // 用户相关
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))   // 有效时间为 10minutes
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // 验证 token

    /**
     * @param token 用户登录带的
     * @param userDetails   spring security loadByUsername 返回的数据库中的
     * @return  token 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // 用户登录带的 token 取出来的
        return (!isTokenExpired(token) && userDetails.getUsername().equals(username));
    }

}
