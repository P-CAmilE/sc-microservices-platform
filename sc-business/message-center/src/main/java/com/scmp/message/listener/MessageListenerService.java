package com.scmp.message.listener;

import com.scmp.domain.DMessage;
import com.scmp.message.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.ErrorMessage;
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
        log.warn("message-center|onMessage|messageContent:{}", dMessage);
        mailService.sendEmailOnMessage(dMessage);
    }

    /**
     * 全局的异常处理：通过订阅全局错误 Channel
     * @param errorMessage errorMessage
     */
    @StreamListener(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
    public void globalHandleError(ErrorMessage errorMessage) {
        log.error("[globalHandleError][payload：{}]", ExceptionUtils.getRootCauseMessage(errorMessage.getPayload()));
        log.error("[globalHandleError][originalMessage：{}]", errorMessage.getOriginalMessage());
        log.error("[globalHandleError][headers：{}]", errorMessage.getHeaders());
    }
}
