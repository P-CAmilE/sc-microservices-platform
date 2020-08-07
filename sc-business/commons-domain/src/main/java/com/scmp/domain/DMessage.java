package com.scmp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Coconut Tree
 * 公文中心 向 消息中心 使用rocketmq 发送消息
 * 消息中应包含：目标用户名 用户部门 用户email 消息体：title + content
 *
 * 如果需要链式赋值：@Accessors(chain = true)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DMessage {

    private String username;
    private String department;
    private String email;
    private String title;
    private String content;
}
