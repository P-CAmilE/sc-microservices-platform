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
        log.error("document-center | Failed-callUserService-checkUserTime | userAccount: {}", userAccount);
        return false;
    }
}
