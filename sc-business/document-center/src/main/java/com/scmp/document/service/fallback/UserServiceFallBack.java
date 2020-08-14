package com.scmp.document.service.fallback;

import com.scmp.document.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Coconut Tree
 */
@Slf4j
@Component
public class UserServiceFallBack implements UserService {

    @Override
    public boolean checkUserTime(String userAccount) {
        log.info("公文中心|调用用户中心|查询用户设定时间|调用失败");
        return false;
    }

//    @Override
//    public String getEmailByUsernameAndDepartment(String department, String username) {
//        log.info("公文中心|调用用户中心|查询用户设定时间|调用失败");
//        return null;
//    }
}
