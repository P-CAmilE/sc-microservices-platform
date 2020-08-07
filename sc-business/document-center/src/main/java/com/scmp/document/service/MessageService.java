package com.scmp.document.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Coconut Tree
 */
@FeignClient("message-service")
public interface MessageService {
}
