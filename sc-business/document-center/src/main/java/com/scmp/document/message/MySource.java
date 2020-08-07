package com.scmp.document.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Coconut Tree
 */
public interface MySource {
    @Output("document-service-output")
    MessageChannel documentServiceOutput();
}
