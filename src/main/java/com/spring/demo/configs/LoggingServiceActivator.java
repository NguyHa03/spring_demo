package com.spring.demo.configs;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class LoggingServiceActivator {

  @ServiceActivator(inputChannel = Names.LOG_CHANNEL)
  public Message<Object> logger(Message<Object> request) throws MessagingException {

    System.out.println(request.toString());

    return MessageBuilder
        .withPayload(request.getPayload())
        .copyHeaders(request.getHeaders())
        .build();

  }

}
