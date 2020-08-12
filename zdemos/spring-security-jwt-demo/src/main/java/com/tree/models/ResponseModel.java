package com.tree.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Coconut Tree
 * 2020/8/11 23:13
 */
@Data
@AllArgsConstructor
public class ResponseModel implements Serializable {
    private final String jwt;
}
