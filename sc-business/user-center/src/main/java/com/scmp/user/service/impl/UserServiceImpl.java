package com.scmp.user.service.impl;

import com.scmp.domain.User;
import com.scmp.domain.exception.UserRepeatException;
import com.scmp.user.dao.UserDao;
import com.scmp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void register(String account, String password) {
        User user = userDao.getUserByAccount(account);
        if(user != null)
            throw new UserRepeatException(201, "用户已存在");
        userDao.register(account, password);
    }

    @Override
    public User login(String account, String password) {
        User user = getUserByAccount(account);
        if(!user.getUserPassword().equals(password))
            throw new UserRepeatException(403, "密码错误");
        return user;
    }

    @Override
    public User updateInfo(User user) {
        userDao.updateInfo(user);
        return getUserById(user.getUserId());
    }

    @Override
    public User updateTime(String userStartTime, String userEndTime, int userId) {
        LocalTime startTime = null;
        LocalTime endTime = null;
        try {
           startTime = LocalTime.parse(userStartTime, TIME_FORMATTER);
            endTime = LocalTime.parse(userEndTime, TIME_FORMATTER);
        }catch (DateTimeParseException e){
            e.printStackTrace();
            throw new UserRepeatException(400, "时间格式参数错误");
        }
        if(before(startTime, endTime))
            throw new UserRepeatException(403, "开始与结束时间顺序错误");
        userDao.updateTime(startTime, endTime, userId);
        return getUserById(userId);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public User getUserByAccount(String userAccount) {
        User user = userDao.getUserByAccount(userAccount);
        if(user == null)
            throw new UserRepeatException(404, "用户不存在");
        return user;
    }

    @Override
    public User getUserById(int userId) {
        User user = userDao.getUserById(userId);
        if(user == null)
            throw new UserRepeatException(404, "用户不存在");
        return user;
    }

    @Override
    public List<User> getUserByDepartmentId(int userDepartmentId) {
        return userDao.getUserByDepartmentId(userDepartmentId);
    }

    @Override
    public boolean checkUserTime(String userAccount) {
        User user = getUserByAccount(userAccount);
        if(user.getUserStartTime() == null && user.getUserEndTime() == null)
            return true;
        LocalTime time = LocalTime.now();
        if(after(time,user.getUserEndTime()) || before(time,user.getUserStartTime()))
            return true;
        return false;     //otherwise: between start and end, or just equals start or end
    }

    /**
     * @param first  first  <code>LocalTime</code>
     * @param second second <code>LocalTime</code>
     * @return <code>true</code> if and only if <tt>first</tt> is <b>strictly</b>
     *          after <tt>second</tt> including <b>hour</b> and <b>minute</b>;
     *         <code>false</code> otherwise.
     *@throws UserRepeatException
     */
    boolean after(LocalTime first, LocalTime second) {
        if(first == null || second ==  null)
            throw new UserRepeatException(500, "用户设置时间错误");
        if(first.getHour() > second.getHour())
            return true;
        else if(first.getHour() == second.getHour()){
            if(first.getMinute() > second.getMinute())
                return true;
            return false; // (minute) equal(=) or less(<) only
        }
        return false;     // (hour) less(<) only
    }

    /**
     * @param first  first  <code>LocalTime</code>
     * @param second second <code>LocalTime</code>
     * @return <code>true</code> if and only if <tt>first</tt> is <b>strictly</b>
     *          before <tt>second</tt> including <b>hour</b> and <b>minute</b>;
     *         <code>false</code> otherwise.
     * @throws UserRepeatException
     */
    boolean before(LocalTime first, LocalTime second) {
        if(first == null || second ==  null)
            throw new UserRepeatException(403, "用户设置时间错误");
        if(first.getHour() < second.getHour())
            return true;
        else if(first.getHour() == second.getHour()){
            if(first.getMinute() < second.getMinute())
                return true;
            return false; // (minute) equal(=) or greater(>) only
        }
        return false;     // (hour) greater(>) only
    }
}
