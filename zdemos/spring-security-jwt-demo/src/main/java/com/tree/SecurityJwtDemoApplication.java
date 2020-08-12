package com.tree;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Coconut Tree
 */
@SpringBootApplication
@MapperScan("com.tree.mapper")
public class SecurityJwtDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtDemoApplication.class, args);
    }
}
