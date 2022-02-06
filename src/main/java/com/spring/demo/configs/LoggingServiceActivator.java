package com.spring.demo.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingServiceActivator {

  @ServiceActivator(inputChannel = ItemChannels.LOG_CHANNEL)
  public Message<Object> logger(Message<Object> request) throws MessagingException {

    log.info("Input request: {}", request.toString());

    return MessageBuilder
        .withPayload(request.getPayload())
        .copyHeaders(request.getHeaders())
        .build();

  }

}
