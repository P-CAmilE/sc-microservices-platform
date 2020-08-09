package com.scmp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

/**
 * @author Coconut Tree
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int userId;
    private String userAccount;
    private String userPassword;
    /**
     * user type, default user.
     * 0 --> normal user (set in database)
     * 1 --> administrator
     */
    private int userType;
    private String userName;
    private int userDepartmentId;
    private Time userStartTime;
    private Time userEndTime;

}
