package com.tree.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Coconut Tree
 * 2020/8/11 23:15
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String HelloWorld() {
        return "Hello World";
    }
}
