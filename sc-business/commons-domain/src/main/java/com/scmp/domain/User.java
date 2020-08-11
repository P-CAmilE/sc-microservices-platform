package com.scmp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

/**
 * @author Coconut Tree
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"userPassword"})
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
    private String userEmail;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime userStartTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime userEndTime;

}
