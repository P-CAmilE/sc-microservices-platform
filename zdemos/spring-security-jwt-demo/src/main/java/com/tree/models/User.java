package com.tree.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author Coconut Tree
 * 2020/8/12 0:16
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("jwt_user")
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    private String username;
    private String password;
    private String authorities;
//    private List<GrantedAuthority> list;
}
