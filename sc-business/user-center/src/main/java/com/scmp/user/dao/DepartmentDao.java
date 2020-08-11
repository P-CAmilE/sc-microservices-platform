package com.scmp.user.dao;

import com.scmp.domain.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentDao {

    @Insert("insert into DEPARTMENT(DEPARTMENT_NAME,DEPARTMENT_INTRO) values(#{departmentName},#{departmentIntro})")
    void add(Department department);

    @Update("update DEPARTMENT set DEPARTMENT_NAME=#{departmentName},DEPARTMENT_INTRO=#{departmentIntro} where DEPARTMENT_ID=#{departmentId}")
    void update(Department department);

    @Select("select * from DEPARTMENT")
    List<Department> getAll();

    @Select("select * from DEPARTMENT where DEPARTMENT_ID=#{departmentId}")
    Department getDepartmentById(@Param("departmentId") int departmentId);

}
