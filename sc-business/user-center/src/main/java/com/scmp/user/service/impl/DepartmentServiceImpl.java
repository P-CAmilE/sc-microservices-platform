package com.scmp.user.service.impl;

import com.scmp.domain.Department;
import com.scmp.user.dao.DepartmentDao;
import com.scmp.user.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public void add(Department department) {
        departmentDao.add(department);
    }

    @Override
    public void update(Department department) {
        departmentDao.update(department);
    }

    @Override
    public List<Department> getAll() {
        return departmentDao.getAll();
    }

    @Override
    public Department getDepartmentById(int departmentId) {
        return departmentDao.getDepartmentById(departmentId);
    }
}
