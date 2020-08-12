package com.tree.controller;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

/**
 * @author Coconut Tree
 * 2020/8/12 0:57
 */
@RestController
@RequestMapping("/anno")
public class AnnotationController {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    public String annoHello() {
        return "hello anno";
    }
    @PermitAll
    @GetMapping("/all")
    public String annoAll() {
        return "hello all";
    }
}
