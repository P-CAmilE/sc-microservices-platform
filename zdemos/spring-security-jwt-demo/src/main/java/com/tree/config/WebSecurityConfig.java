package com.tree.config;

import com.tree.Filter.JwtRequestFilter;
import com.tree.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Coconut Tree
 * 2020/8/11 23:20
 */
@Configuration
// 允许在方法上通过注解进行权限验证
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/*").permitAll()
                .antMatchers("/admin/*").hasRole("ADMIN")
                // spring security 默认拥有 ROLE_ADMIN 权限的用户可以访问 ROLE_USER 的资源
                .antMatchers("/hello/*").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .and()
                // 使springSecurity 不要创建 session，这也是 使用 jwt 的初衷
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // session 不用的话，得有能进行 认证的，所以注册 自定义filter
        // 每次请求必须加上 Authenrization Header，没有session，每次请求都要进行验证
        // 在 spring security 认证用户名密码前 使用 自定义jwt认证，并将认证结果保存到 securityContextHolder
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
