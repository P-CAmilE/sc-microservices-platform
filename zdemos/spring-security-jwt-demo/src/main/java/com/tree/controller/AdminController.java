package com.tree.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Coconut Tree
 * 2020/8/11 23:52
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/hello")
    public String helloAdmin() {
        return "hello admin";
    }
}
