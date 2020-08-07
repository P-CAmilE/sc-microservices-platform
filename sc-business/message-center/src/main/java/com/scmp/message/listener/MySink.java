package com.scmp.message.listener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Coconut Tree
 */
public interface MySink {
    String MESSAGE_SERVICE_INPUT = "message-service-input";

    @Input(MESSAGE_SERVICE_INPUT)
    SubscribableChannel messageServiceInput();
}
