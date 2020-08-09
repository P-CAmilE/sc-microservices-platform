package com.scmp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Coconut Tree
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private int departmentId;
    private String departmentName;
    private String departmentIntro;

}
