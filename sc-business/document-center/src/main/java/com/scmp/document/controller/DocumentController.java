package com.scmp.document.controller;

import com.scmp.document.message.MySource;
import com.scmp.document.service.UserService;
import com.scmp.domain.DMessage;
import com.scmp.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Coconut Tree
 */
@Slf4j
@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    UserService userService;

    @Autowired
    MySource mySource;

    /**
     * 日志应该记录的是：什么时候 向谁发送 公文-title
     */

    /**
     * 发送公文
     * @param department    目标用户-部门
     * @param username      目标用户-用户名
     * @param email         目标用户-邮箱地址
     * @param title         公文标题
     * @param content       公文内容
     * @return      发送结果
     * 1.如果需要发送邮件附件，之后再加上：@RequestParam("file") MultipartFile file
     * 2.熔断：由 sentinel 保证
     * 3.补偿：rocketmq 自带消息补偿重试机制(集群模式下）
     */
    @PostMapping("/sendDocument")
    public Result sendDocument(@RequestParam("department") String department,
                               @RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content) {
        // info warn
        log.warn("document-center|onRequest|targerUser: {}-{}-{}", department, username, email);
        try {
            boolean match = userService.checkUserTime(username);
            if (!match) {
                log.warn("document-center|call-User-Service-checkUserTime | result: {}", match);
                return Result.failed("发送失败，目标用户现不接收消息，请稍后再试");
            }
        } catch (Exception e) {
            log.error("document-center|failed: call-User-Service-checkUserTime | cause: {}", e.toString());
            return Result.failed("公文发送|发送失败|原因:查询用户接收时间失败");
        }
        
        DMessage dMessage = new DMessage();
        dMessage.setDepartment(department);
        dMessage.setUsername(username);
        dMessage.setTitle(title);
        dMessage.setContent(content);
        dMessage.setEmail(email);
        // 构建 SpringMessage
        Message<DMessage> springMessage = MessageBuilder
                .withPayload(dMessage)
                .build();
        boolean sendResult = mySource.documentServiceOutput().send(springMessage);
        if (!sendResult) {
            log.error("document-center| Failed-sendMessageToRocketMQ | cause: {}", "RocketMQ Error");
            return Result.failed("公文发送|发送失败|原因:RocketMQ发送消息异常");
        }
        log.warn("document-center| Success-sendMessageToRocketMQ | MessageTargerEmail: {}", email);
        return Result.succeed("公文发送|发送成功");
    }
}
