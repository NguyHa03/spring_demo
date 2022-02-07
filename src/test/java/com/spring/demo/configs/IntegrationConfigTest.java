package com.spring.demo.configs;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.MessageChannels;

@ExtendWith(MockitoExtension.class)
class IntegrationConfigTest {

  @InjectMocks
  private IntegrationConfig integrationConfig;
  @Mock
  private DirectChannelSpec directChannelSpec;
  @Mock
  private DirectChannel directChannel;

  @Test
  void inputChannelShouldReturnDirectChannel() {
    try (MockedStatic<MessageChannels> channelsMockedStatic = mockStatic(MessageChannels.class)) {
      when(directChannelSpec.get()).thenReturn(directChannel);
      channelsMockedStatic.when(
          () -> MessageChannels.direct(ItemChannels.INPUT_CHANNEL)).thenReturn(directChannelSpec);
      assertThat(integrationConfig.inputChannel()).isEqualTo(directChannel);
    }

  }

}