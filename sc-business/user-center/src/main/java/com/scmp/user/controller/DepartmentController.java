package com.scmp.user.controller;

import com.scmp.domain.Department;
import com.scmp.domain.ResultVO;
import com.scmp.user.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add")
    public ResultVO<Object> add(@RequestBody Department department) {
        departmentService.add(department);
        return new ResultVO<>(200, "success", null);
    }

    @PostMapping("/update")
    public ResultVO<Object> update(@RequestBody Department department) {
        departmentService.update(department);
        return new ResultVO<>(200, "success", null);
    }

    @GetMapping("/getAll")
    public ResultVO<Object> getAll() {
        return new ResultVO<>(200, "success", departmentService.getAll());
    }

    @GetMapping("/getById")
    public ResultVO<Object> getDepartmentById(@RequestParam("departmentId") int departmentId) {
        return new ResultVO<>(200, "success", departmentService.getDepartmentById(departmentId));
    }
}
