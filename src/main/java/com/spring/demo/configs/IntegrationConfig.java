package com.spring.demo.configs;

import com.spring.demo.models.Item;
import com.spring.demo.services.ItemService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class IntegrationConfig {

  public static final String NONEXISTENT_ITEM_ID = "unreal";
  public static final int NONEXISTENT_ITEM_AMOUNT = -1;
  public static final String NONEXISTENT_ITEM_NAME = "The item you are looking for does not exist";
  private final ItemService itemService;
  private final LoggingServiceActivator loggingServiceActivator;

  @Autowired
  public IntegrationConfig(ItemService itemService,
      LoggingServiceActivator loggingServiceActivator) {
    this.itemService = itemService;
    this.loggingServiceActivator = loggingServiceActivator;
  }

  @Bean(name = ItemChannels.INPUT_CHANNEL)
  public DirectChannel inputChannel() {
    return MessageChannels.direct(ItemChannels.INPUT_CHANNEL).get();
  }

  @Bean(name = ItemChannels.OUTPUT_CHANNEL)
  public DirectChannel outputChannel() {
    return MessageChannels.direct(ItemChannels.OUTPUT_CHANNEL).get();
  }

  @Bean(name = ItemChannels.ITEM_ROUTER_CHANNEL)
  public DirectChannel itemRouterChannel() {
    return MessageChannels.direct(ItemChannels.ITEM_ROUTER_CHANNEL).get();
  }

  @Bean(name = ItemChannels.GET_ITEMS_CHANNEL)
  public DirectChannel getItemsChannel() {
    return MessageChannels.direct(ItemChannels.GET_ITEMS_CHANNEL).get();
  }

  @Bean(name = ItemChannels.GET_ITEM_BY_ID_CHANNEL)
  public DirectChannel getItemByIdChannel() {
    return MessageChannels.direct(ItemChannels.GET_ITEM_BY_ID_CHANNEL).get();
  }

  @Bean(name = ItemChannels.ADD_NEW_ITEM_CHANNEL)
  public DirectChannel addNewItemChannel() {
    return MessageChannels.direct(ItemChannels.ADD_NEW_ITEM_CHANNEL).get();
  }

  @Bean(name = ItemChannels.UPDATE_ITEM_BY_ID_CHANNEL)
  public DirectChannel updateItemByIdChannel() {
    return MessageChannels.direct(ItemChannels.UPDATE_ITEM_BY_ID_CHANNEL).get();
  }

  @Bean(name = ItemChannels.DELETE_ITEM_BY_ID_CHANNEL)
  public DirectChannel deleteItemByIdChannel() {
    return MessageChannels.direct(ItemChannels.DELETE_ITEM_BY_ID_CHANNEL).get();
  }

  @Bean(name = ItemChannels.LOG_CHANNEL)
  public DirectChannel logChannel() {
    return MessageChannels.direct(ItemChannels.LOG_CHANNEL).get();
  }

  @Bean
  public IntegrationFlow itemFlow() {
    return IntegrationFlows.from(ItemChannels.INPUT_CHANNEL)
        .handle(new ServiceActivatingHandler(loggingServiceActivator))
        .channel(ItemChannels.ITEM_ROUTER_CHANNEL)
        .get();
  }

  @ServiceActivator(inputChannel = ItemChannels.ITEM_ROUTER_CHANNEL)
  @Bean
  public HeaderValueRouter itemRouter() {
    HeaderValueRouter itemRouter = new HeaderValueRouter(ItemChannels.API_HEADER_KEY);
    itemRouter.setChannelMapping(ItemChannels.API_HEADER_VALUE_GET_ITEMS,
        ItemChannels.GET_ITEMS_CHANNEL);
    itemRouter.setChannelMapping(ItemChannels.API_HEADER_VALUE_GET_ITEM_BY_ID,
        ItemChannels.GET_ITEM_BY_ID_CHANNEL);
    itemRouter.setChannelMapping(ItemChannels.API_HEADER_VALUE_ADD_NEW_ITEM,
        ItemChannels.ADD_NEW_ITEM_CHANNEL);
    itemRouter.setChannelMapping(ItemChannels.API_HEADER_VALUE_UPDATE_ITEM_BY_ID,
        ItemChannels.UPDATE_ITEM_BY_ID_CHANNEL);
    itemRouter.setChannelMapping(ItemChannels.API_HEADER_VALUE_DELETE_ITEM_BY_ID,
        ItemChannels.DELETE_ITEM_BY_ID_CHANNEL);
    return itemRouter;
  }

  @ServiceActivator(inputChannel = ItemChannels.GET_ITEMS_CHANNEL)
  public Message<List<Item>> getItemsServiceActivator(Message<String> request)
      throws MessagingException {
    return MessageBuilder
        .withPayload(itemService.getItems(request.getPayload()))
        .copyHeaders(request.getHeaders())
        .build();
  }

  @ServiceActivator(inputChannel = ItemChannels.GET_ITEM_BY_ID_CHANNEL)
  public Message<Item> getItemByIdServiceActivator(Message<String> request)
      throws MessagingException {

    Item searchResult = itemService.getItemById(request.getPayload());

    return MessageBuilder
        .withPayload(Objects.requireNonNullElseGet(searchResult, () -> new Item(
            NONEXISTENT_ITEM_ID,
            NONEXISTENT_ITEM_AMOUNT,
            NONEXISTENT_ITEM_NAME
        )))
        .copyHeaders(request.getHeaders())
        .build();

  }

  @ServiceActivator(inputChannel = ItemChannels.ADD_NEW_ITEM_CHANNEL)
  public Message<com.spring.demo.configs.Message> addNewItemServiceActivator(Message<Item> request)
      throws MessagingException {
    return MessageBuilder
        .withPayload(itemService.addNewItem(request.getPayload()))
        .copyHeaders(request.getHeaders())
        .build();
  }

  @ServiceActivator(inputChannel = ItemChannels.UPDATE_ITEM_BY_ID_CHANNEL)
  public Message<com.spring.demo.configs.Message> updateItemByIdServiceActivator(
      Message<Item> request)
      throws MessagingException {
    return MessageBuilder
        .withPayload(itemService.updateItemById(request.getPayload()))
        .copyHeaders(request.getHeaders())
        .build();
  }

  @ServiceActivator(inputChannel = ItemChannels.DELETE_ITEM_BY_ID_CHANNEL)
  public Message<com.spring.demo.configs.Message> deleteItemByIdServiceActivator(
      Message<String> request)
      throws MessagingException {
    return MessageBuilder
        .withPayload(itemService.deleteItemById(request.getPayload()))
        .copyHeaders(request.getHeaders())
        .build();
  }

}
