package com.scmp.user.controller;

import com.scmp.domain.ResultVO;
import com.scmp.domain.User;
import com.scmp.user.service.UserService;
import com.scmp.user.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResultVO<Object> register(@RequestParam("userAccount") String userAccount,
                                     @RequestParam("userPassword") String userPassword) {
        userService.register(userAccount, userPassword);
        return new ResultVO<>(200, "success", null);
    }

    @PostMapping("/login")
    public ResultVO<User> login(@RequestParam("userAccount") String userAccount,
                                  @RequestParam("userPassword") String userPassword) {
        User user = userService.login(userAccount, userPassword);
        return new ResultVO<>(200, TokenUtil.getToken(user), user);
    }

    @PostMapping("/updateInfo")
    public ResultVO<Object> updateInfo(@RequestBody User user) {
        return new ResultVO<>(200, "success", userService.updateInfo(user));
    }

    @PostMapping("/updateTime")
    public ResultVO<Object> updateTime(@RequestParam("userStartTime") String userStartTime,
                                       @RequestParam("userEndTime")  String userEndTime,
                                       @RequestParam("userId") int userId) {
        return new ResultVO<>(200, "success", userService.updateTime(userStartTime, userEndTime, userId));
    }

    @GetMapping("/getAll")
    public ResultVO<Object> getAllUser() {
        return new ResultVO<>(200, "success", userService.getAllUser());
    }

    @GetMapping("/getByAccount")
    public ResultVO<Object> getUserByAccount(@RequestParam("userAccount") String userAccount) {
        return new ResultVO<>(200, "success", userService.getUserByAccount(userAccount));
    }

    @GetMapping("/getById")
    public ResultVO<Object> getUserById(@RequestParam("userId") int userId) {
        return new ResultVO<>(200, "success", userService.getUserById(userId));
    }

    @GetMapping("/getByDepartmentId")
    public ResultVO<Object> getUserByDepartmentId(@RequestParam("userDepartmentId") int userDepartmentId) {
        return new ResultVO<>(200, "success", userService.getUserByDepartmentId(userDepartmentId));
    }

    @GetMapping("/VerifyToken")
    public ResultVO<Object> verifyToken(@RequestParam("token") String token) {
        return new ResultVO<>(200, "success", userService.verifyToken(token));
    }

    /**
     * @param userAccount
     * @return <code>true</code> if and only if <tt>nowTime</tt> is
     *          <b>not</b> between <tt>userStartTime</tt> and
     *          <tt>userEndTime</tt>;
     *         <code>false</code> otherwise.
     */
    @GetMapping("/checkUserTime")
    public boolean checkUserTime(@RequestParam("userAccount") String userAccount) {
        return userService.checkUserTime(userAccount);
    }
}
