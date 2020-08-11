package com.scmp.user.service;

import com.scmp.domain.Department;

import java.util.List;

public interface DepartmentService {

    void add(Department department);

    void update(Department department);

    List<Department> getAll();

    Department getDepartmentById(int departmentId);

}
