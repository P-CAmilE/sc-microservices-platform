package com.scmp.user.controller;

import com.scmp.domain.ResultVO;
import com.scmp.domain.User;
import com.scmp.user.service.UserService;
import com.scmp.user.utils.TokenUtil;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResultVO<Object> register(@RequestParam("account") String account,
                                     @RequestParam("password") String password) {
        userService.register(account, password);
        return new ResultVO<>(200, "success", null);
    }

    @PostMapping("/login")
    public ResultVO<Object> login(@RequestParam("account") String account,
                                  @RequestParam("password") String password) {
        User user = userService.login(account, password);
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

    @PostMapping("/VerifyToken")
    public ResultVO<Object> verifyToken(@RequestParam("token") String token) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            resultMap = TokenUtil.verifyToken(token);
            return new ResultVO<>(200, "success", resultMap);
        }catch (SignatureVerificationException e){
            e.printStackTrace();
            return new ResultVO<>(200, "success", resultMap);
        }
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

//    @GetMapping("/getEmailByUsernameAndDepartment")
//    String getEmailByUsernameAndDepartment(@RequestParam("department") String department,
//                                           @RequestParam("username") String username) {
//        return "codefish@whu.edu.cn";
//    }
}
