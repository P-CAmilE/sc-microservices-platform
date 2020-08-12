package com.tree.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Coconut Tree
 * 2020/8/11 23:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestModel {
    private String username;
    private String password;
}
