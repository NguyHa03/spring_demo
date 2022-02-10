package com.spring.demo.configs;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spring.demo.models.Item;
import com.spring.demo.services.ItemService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.BaseIntegrationFlowDefinition;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@ExtendWith(MockitoExtension.class)
class IntegrationConfigTest {

  public static final String DUMMY_HEADER_KEY = "dummy header key";
  public static final String DUMMY_HEADER_VALUE = "dummy header value";
  public static final String DUMMY_STRING_PAYLOAD = "dummy string payload";

  @InjectMocks
  private IntegrationConfig integrationConfigObjectUnderTest;

  @Mock
  private DirectChannelSpec directChannelSpecMockObject;
  @Mock
  private DirectChannel directChannelMockObject;
  @Mock
  private IntegrationFlow integrationFlowMockObject;
  @Mock
  private IntegrationFlows integrationFlowsMockObject;
  @Mock
  private StandardIntegrationFlow standardIntegrationFlowMockObject;
  @Mock
  private IntegrationFlowBuilder integrationFlowBuilderMockObject;
  @Mock
  private BaseIntegrationFlowDefinition baseIntegrationFlowDefinitionMockObject;
  @Mock
  private ItemService itemServiceMockObject;
  @Mock
  private Message<String> stringMessageMockObject;
  @Mock
  private List<Item> itemListMockObject;
  @Mock
  private Item itemMockObject;
  @Mock
  private Message<Item> itemMessageMockObject;
  @Mock
  private ApiResponseMessage apiResponseMessageMockObject;

  @Captor
  private ArgumentCaptor<String> stringArgumentCaptor;
  @Captor
  private ArgumentCaptor<Item> itemArgumentCaptor;

  @Test
  public void inputChannelShouldReturnDirectChannel() {
    try (MockedStatic<MessageChannels> channelsMockedStatic = mockStatic(MessageChannels.class)) {
      when(directChannelSpecMockObject.get()).thenReturn(directChannelMockObject);
      channelsMockedStatic.when(
          () -> MessageChannels.direct(ItemChannels.INPUT_CHANNEL)).thenReturn(
          directChannelSpecMockObject);
      assertThat(integrationConfigObjectUnderTest.inputChannel()).isEqualTo(
          directChannelMockObject);
    }
  }

  @Test
  public void outputChannelShouldReturnDirectChannel() {
    try (MockedStatic<MessageChannels> channelsMockedStatic = mockStatic(MessageChannels.class)) {
      when(directChannelSpecMockObject.get()).thenReturn(directChannelMockObject);
      channelsMockedStatic.when(
          () -> MessageChannels.direct(ItemChannels.OUTPUT_CHANNEL)).thenReturn(
          directChannelSpecMockObject);
      assertThat(integrationConfigObjectUnderTest.outputChannel()).isEqualTo(
          directChannelMockObject);
    }
  }

  @Test
  public void itemRouterChannelShouldReturnDirectChannel() {
    try (MockedStatic<MessageChannels> channelsMockedStatic = mockStatic(MessageChannels.class)) {
      when(directChannelSpecMockObject.get()).thenReturn(directChannelMockObject);
      channelsMockedStatic.when(
          () -> MessageChannels.direct(ItemChannels.ITEM_ROUTER_CHANNEL)).thenReturn(
          directChannelSpecMockObject);
      assertThat(integrationConfigObjectUnderTest.itemRouterChannel()).isEqualTo(
          directChannelMockObject);
    }
  }

  @Test
  public void getItemsChannelShouldReturnDirectChannel() {
    try (MockedStatic<MessageChannels> channelsMockedStatic = mockStatic(MessageChannels.class)) {
      when(directChannelSpecMockObject.get()).thenReturn(directChannelMockObject);
      channelsMockedStatic.when(
          () -> MessageChannels.direct(ItemChannels.GET_ITEMS_CHANNEL)).thenReturn(
          directChannelSpecMockObject);
      assertThat(integrationConfigObjectUnderTest.getItemsChannel()).isEqualTo(
          directChannelMockObject);
    }
  }

  @Test
  public void getItemByIdChannelShouldReturnDirectChannel() {
    try (MockedStatic<MessageChannels> channelsMockedStatic = mockStatic(MessageChannels.class)) {
      when(directChannelSpecMockObject.get()).thenReturn(directChannelMockObject);
      channelsMockedStatic.when(
          () -> MessageChannels.direct(ItemChannels.GET_ITEM_BY_ID_CHANNEL)).thenReturn(
          directChannelSpecMockObject);
      assertThat(integrationConfigObjectUnderTest.getItemByIdChannel()).isEqualTo(
          directChannelMockObject);
    }
  }

  @Test
  public void addNewItemChannelShouldReturnDirectChannel() {
    try (MockedStatic<MessageChannels> channelsMockedStatic = mockStatic(MessageChannels.class)) {
      when(directChannelSpecMockObject.get()).thenReturn(directChannelMockObject);
      channelsMockedStatic.when(
          () -> MessageChannels.direct(ItemChannels.ADD_NEW_ITEM_CHANNEL)).thenReturn(
          directChannelSpecMockObject);
      assertThat(integrationConfigObjectUnderTest.addNewItemChannel()).isEqualTo(
          directChannelMockObject);
    }
  }

  @Test
  public void updateItemByIdChannelShouldReturnDirectChannel() {
    try (MockedStatic<MessageChannels> channelsMockedStatic = mockStatic(MessageChannels.class)) {
      when(directChannelSpecMockObject.get()).thenReturn(directChannelMockObject);
      channelsMockedStatic.when(
          () -> MessageChannels.direct(ItemChannels.UPDATE_ITEM_BY_ID_CHANNEL)).thenReturn(
          directChannelSpecMockObject);
      assertThat(integrationConfigObjectUnderTest.updateItemByIdChannel()).isEqualTo(
          directChannelMockObject);
    }
  }

  @Test
  public void deleteItemByIdChannelShouldReturnDirectChannel() {
    try (MockedStatic<MessageChannels> channelsMockedStatic = mockStatic(MessageChannels.class)) {
      when(directChannelSpecMockObject.get()).thenReturn(directChannelMockObject);
      channelsMockedStatic.when(
          () -> MessageChannels.direct(ItemChannels.DELETE_ITEM_BY_ID_CHANNEL)).thenReturn(
          directChannelSpecMockObject);
      assertThat(integrationConfigObjectUnderTest.deleteItemByIdChannel()).isEqualTo(
          directChannelMockObject);
    }
  }

  @Test
  public void logChannelShouldReturnDirectChannel() {
    try (MockedStatic<MessageChannels> channelsMockedStatic = mockStatic(MessageChannels.class)) {
      when(directChannelSpecMockObject.get()).thenReturn(directChannelMockObject);
      channelsMockedStatic.when(
          () -> MessageChannels.direct(ItemChannels.LOG_CHANNEL)).thenReturn(
          directChannelSpecMockObject);
      assertThat(integrationConfigObjectUnderTest.logChannel()).isEqualTo(directChannelMockObject);
    }
  }

//  @Test
//  public void itemFlowShouldReturnIntegrationFlow() {
//    try (MockedStatic<IntegrationFlows> integrationFlowsMockedStatic = mockStatic(
//        IntegrationFlows.class)) {
//
//      integrationFlowsMockedStatic.when(() -> IntegrationFlows.from(ItemChannels.INPUT_CHANNEL))
//          .thenReturn(integrationFlowBuilderMockObject);
////      when(integrationFlowBuilderMockObject.channel(ItemChannels.ITEM_ROUTER_CHANNEL)).thenReturn();
//      when(integrationFlowBuilderMockObject.get()).thenReturn(standardIntegrationFlowMockObject);
//
//      IntegrationFlow actualIntegrationFlow = integrationConfigObjectUnderTest.itemFlow();
//
////      verify(baseIntegrationFlowDefinitionMockObject).channel(itemChannelsArgumentCaptor.capture());
////      String itemChannelsArgumentCaptorValue = itemChannelsArgumentCaptor.getValue();
////      assertThat(itemChannelsArgumentCaptorValue).isEqualTo(ItemChannels.ITEM_ROUTER_CHANNEL);
//
//      verify(integrationFlowBuilderMockObject).get();
//
//      assertThat(actualIntegrationFlow).isEqualTo(standardIntegrationFlowMockObject);
//
//    }
//  }

  @Test
  public void getItemsServiceActivatorShouldReturnItemListMessage() {
    MessageHeaders messageHeaders = new MessageHeaders(
        Map.of(DUMMY_HEADER_KEY, DUMMY_HEADER_VALUE));
    when(stringMessageMockObject.getPayload()).thenReturn(DUMMY_STRING_PAYLOAD);
    when(stringMessageMockObject.getHeaders()).thenReturn(messageHeaders);
    when(itemServiceMockObject.getItems(stringMessageMockObject.getPayload())).thenReturn(
        itemListMockObject);

    Message<List<Item>> actualItemListMessage = integrationConfigObjectUnderTest.getItemsServiceActivator(
        stringMessageMockObject);

    verify(itemServiceMockObject).getItems(stringArgumentCaptor.capture());
    assertThat(stringArgumentCaptor.getValue()).isEqualTo(DUMMY_STRING_PAYLOAD);

    assertThat(actualItemListMessage.getPayload()).isEqualTo(itemListMockObject);
    assertThat(actualItemListMessage.getHeaders().containsKey(DUMMY_HEADER_KEY)).isTrue();
  }

  @Test
  public void getItemByIdServiceActivatorShouldReturnItemMessageWhenItemFound() {
    MessageHeaders messageHeaders = new MessageHeaders(
        Map.of(DUMMY_HEADER_KEY, DUMMY_HEADER_VALUE));
    when(stringMessageMockObject.getPayload()).thenReturn(DUMMY_STRING_PAYLOAD);
    when(stringMessageMockObject.getHeaders()).thenReturn(messageHeaders);
    when(itemServiceMockObject.getItemById(stringMessageMockObject.getPayload())).thenReturn(
        itemMockObject);

    Message<Item> actualItemMessage = integrationConfigObjectUnderTest.getItemByIdServiceActivator(
        stringMessageMockObject);

    verify(itemServiceMockObject).getItemById(stringArgumentCaptor.capture());
    assertThat(stringArgumentCaptor.getValue()).isEqualTo(DUMMY_STRING_PAYLOAD);

    assertThat(actualItemMessage.getPayload()).isEqualTo(itemMockObject);
    assertThat(actualItemMessage.getHeaders().containsKey(DUMMY_HEADER_KEY)).isTrue();
  }

  @Test
  public void getItemByIdServiceActivatorShouldReturnNonexistentItemMessageWhenItemNotFound() {
    MessageHeaders messageHeaders = new MessageHeaders(
        Map.of(DUMMY_HEADER_KEY, DUMMY_HEADER_VALUE));
    when(stringMessageMockObject.getPayload()).thenReturn(DUMMY_STRING_PAYLOAD);
    when(stringMessageMockObject.getHeaders()).thenReturn(messageHeaders);
    when(itemServiceMockObject.getItemById(stringMessageMockObject.getPayload())).thenReturn(null);

    Message<Item> actualItemMessage = integrationConfigObjectUnderTest.getItemByIdServiceActivator(
        stringMessageMockObject);

    verify(itemServiceMockObject).getItemById(stringArgumentCaptor.capture());
    assertThat(stringArgumentCaptor.getValue()).isEqualTo(DUMMY_STRING_PAYLOAD);

    Item nonexistentItem = new Item(
        IntegrationConfig.NONEXISTENT_ITEM_ID,
        IntegrationConfig.NONEXISTENT_ITEM_AMOUNT,
        IntegrationConfig.NONEXISTENT_ITEM_NAME
    );
    assertThat(actualItemMessage.getPayload().getId()).isEqualTo(nonexistentItem.getId());
    assertThat(actualItemMessage.getPayload().getAmount()).isEqualTo(nonexistentItem.getAmount());
    assertThat(actualItemMessage.getPayload().getName()).isEqualTo(nonexistentItem.getName());

    assertThat(actualItemMessage.getHeaders().containsKey(DUMMY_HEADER_KEY)).isTrue();
  }

  @Test
  public void addNewItemServiceActivatorShouldReturnApiResponseMessageMessage() {
    MessageHeaders messageHeaders = new MessageHeaders(
        Map.of(DUMMY_HEADER_KEY, DUMMY_HEADER_VALUE));
    when(itemMessageMockObject.getPayload()).thenReturn(itemMockObject);
    when(itemMessageMockObject.getHeaders()).thenReturn(messageHeaders);
    when(itemServiceMockObject.addNewItem(itemMessageMockObject.getPayload())).thenReturn(
        apiResponseMessageMockObject);

    Message<ApiResponseMessage> actualApiResponseMessageMessage = integrationConfigObjectUnderTest.addNewItemServiceActivator(
        itemMessageMockObject);

    verify(itemServiceMockObject).addNewItem(itemArgumentCaptor.capture());
    assertThat(itemArgumentCaptor.getValue()).isEqualTo(itemMockObject);

    assertThat(actualApiResponseMessageMessage.getPayload()).isEqualTo(
        apiResponseMessageMockObject);
    assertThat(actualApiResponseMessageMessage.getHeaders().containsKey(DUMMY_HEADER_KEY)).isTrue();
  }

  @Test
  public void updateItemByIdServiceActivatorShouldReturnApiResponseMessageMessage() {
    MessageHeaders messageHeaders = new MessageHeaders(
        Map.of(DUMMY_HEADER_KEY, DUMMY_HEADER_VALUE));
    when(itemMessageMockObject.getPayload()).thenReturn(itemMockObject);
    when(itemMessageMockObject.getHeaders()).thenReturn(messageHeaders);
    when(itemServiceMockObject.updateItemById(itemMessageMockObject.getPayload())).thenReturn(
        apiResponseMessageMockObject);

    Message<ApiResponseMessage> actualApiResponseMessageMessage = integrationConfigObjectUnderTest.updateItemByIdServiceActivator(
        itemMessageMockObject);

    verify(itemServiceMockObject).updateItemById(itemArgumentCaptor.capture());
    assertThat(itemArgumentCaptor.getValue()).isEqualTo(itemMockObject);

    assertThat(actualApiResponseMessageMessage.getPayload()).isEqualTo(
        apiResponseMessageMockObject);
    assertThat(actualApiResponseMessageMessage.getHeaders().containsKey(DUMMY_HEADER_KEY)).isTrue();
  }

  @Test
  public void deleteItemByIdServiceActivatorShouldReturnApiResponseMessageMessage() {
    MessageHeaders messageHeaders = new MessageHeaders(
        Map.of(DUMMY_HEADER_KEY, DUMMY_HEADER_VALUE));
    when(stringMessageMockObject.getPayload()).thenReturn(DUMMY_STRING_PAYLOAD);
    when(stringMessageMockObject.getHeaders()).thenReturn(messageHeaders);
    when(itemServiceMockObject.deleteItemById(stringMessageMockObject.getPayload())).thenReturn(
        apiResponseMessageMockObject);

    Message<ApiResponseMessage> actualApiResponseMessageMessage = integrationConfigObjectUnderTest.deleteItemByIdServiceActivator(
        stringMessageMockObject);

    verify(itemServiceMockObject).deleteItemById(stringArgumentCaptor.capture());
    assertThat(stringArgumentCaptor.getValue()).isEqualTo(DUMMY_STRING_PAYLOAD);

    assertThat(actualApiResponseMessageMessage.getPayload()).isEqualTo(
        apiResponseMessageMockObject);
    assertThat(actualApiResponseMessageMessage.getHeaders().containsKey(DUMMY_HEADER_KEY)).isTrue();
  }

}
