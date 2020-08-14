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
     * @param file          selectable; but request <b>must</b> contain this,
     *                       <code>null</code> when don't seed a file.
     * @return      发送结果
     * 如果需要发送邮件附件，之后再加上：@RequestParam("file") MultipartFile file
     */
    @PostMapping("/sendDocument")
    public Result sendDocument(@RequestParam("department") String department,
                               @RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam("file") MultipartFile file) {
        // info warn
        log.info("公文发送|接收到发送公文的请求目标发送对象为: {}--{}", department, username);

        log.info("公文发送|调用用户微服务|查看当前时间是否在用户设定接收范围内");
        try {
            /**
             * 其实也可以传回用户设置的 endTime，也可以提醒
             */
            boolean match = userService.checkUserTime(username);

            log.info("公文发送|调用用户微服务成功|查询结果为: {}", match);
            if (!match) {
                log.info("公文发送|调用用户中心失败|原因:目标用户现不接收消息");
                return Result.failed("发送失败，目标用户现不接收消息，请稍后再试");
            }
        } catch (Exception e) {
            log.error("send error: {}", e.toString());
            return Result.failed("公文发送|发送失败|原因:查询用户接收时间失败");
        }

//        log.info("公文发送|调用用户微服务|查询用户邮箱地址");
//        String userEmail = userService.getEmailByUsernameAndDepartment(department, username);
//        if (userEmail == null) {
//            log.info("公文发送|调用用户中心|查询用户邮箱地址失败");
//            return Result.failed("公文发送|发送失败|原因:查询用户邮箱地址失败");
//        }

        // TODO: 2020/8/13 if(file != null)
        
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
        log.info("公文发送|向消息中心微服务发送消息|结果为：{}", sendResult);
        if (!sendResult) {
            log.info("公文发送|向消息中心微服务发送消息|RocketMQ发送消息异常");
            return Result.failed("公文发送|发送失败|原因:RocketMQ发送消息异常");
        }
        return Result.succeed("公文发送|发送成功");
    }
}
