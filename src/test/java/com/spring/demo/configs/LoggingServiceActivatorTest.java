package com.spring.demo.configs;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@ExtendWith({MockitoExtension.class})
class LoggingServiceActivatorTest {

  public static final String DATA = "data";
  public static final String KEY = "key";
  public static final String VALUE = "value";

  @Mock
  private Message<Object> request;

  @Test
  public void loggerShouldReturnMessageObject() {
    MessageHeaders messageHeaders = new MessageHeaders(Map.of(KEY, VALUE));
    when(request.getPayload()).thenReturn(DATA);
    when(request.getHeaders()).thenReturn(messageHeaders);

    LoggingServiceActivator loggingServiceActivator = new LoggingServiceActivator();
    Message<Object> objectMessage = loggingServiceActivator.logger(request);
    assertThat(objectMessage.getPayload()).isEqualTo(DATA);
    assertThat(objectMessage.getHeaders().containsKey(KEY)).isTrue();
  }
}