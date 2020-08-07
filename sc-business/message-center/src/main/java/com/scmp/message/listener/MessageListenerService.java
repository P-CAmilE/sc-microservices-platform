package com.scmp.message.listener;

import com.scmp.domain.DMessage;
import com.scmp.message.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author Coconut Tree
 */
@Slf4j
@Component
public class MessageListenerService {

    @Autowired
    MailService mailService;

    @StreamListener(MySink.MESSAGE_SERVICE_INPUT)
    public void onMessage(@Payload DMessage dMessage) {
        log.info("[onMessage][消息内容：{}][现在可以发送邮件了]", dMessage);
        mailService.sendEmailOnMessage(dMessage);
    }
}
