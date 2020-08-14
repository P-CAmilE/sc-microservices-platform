package com.scmp.user.dao;

import com.scmp.domain.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalTime;
import java.util.List;

@Mapper
public interface UserDao {

    @Insert("insert into USER(USER_ACCOUNT,USER_PASSWORD) values(#{account},#{password})")
    void register(@Param("account") String account, @Param("password") String password);

    @Update("update USER set USER_NAME=#{userName},USER_DEPARTMENT_ID=#{userDepartmentId},USER_EMAIL=#{userEmail} where USER_ID=#{userId}")
    void updateInfo(User user);

    @Update("update USER set USER_START_TIME=#{userStartTime},USER_END_TIME=#{userEndTime} where USER_ID=#{userId}")
    void updateTime(@Param("userStartTime") LocalTime userStartTime, @Param("userEndTime") LocalTime userEndTime, @Param("userId") int userId);

    @Select("select * from USER")
    List<User> getAllUser();

    @Select("select * from USER where USER_ACCOUNT=#{userAccount}")
    User getUserByAccount(@Param("userAccount") String userAccount);

    @Select("select * from USER where USER_ID=#{userId}")
    User getUserById(@Param("userId") int userId);

    @Select("select * from USER where USER_DEPARTMENT_ID=#{userDepartmentId}")
    List<User> getUserByDepartmentId(@Param("userDepartmentId") int userDepartmentId);

}
