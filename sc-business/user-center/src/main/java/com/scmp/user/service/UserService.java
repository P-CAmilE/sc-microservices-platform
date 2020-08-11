package com.scmp.user.service;

import com.scmp.domain.User;

import java.util.List;

public interface UserService {

    void register(String account, String password);

    User login(String account, String password);

    User updateInfo(User user);

    User updateTime(String userStartTime, String userEndTime, int userId);

    List<User> getAllUser();

    User getUserByAccount(String userAccount);

    User getUserById(int userId);

    List<User> getUserByDepartmentId(int userDepartmentId);

    boolean checkUserTime(String userAccount);

}
