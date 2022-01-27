package com.spring.demo.configs;

import com.spring.demo.models.Item;
import com.spring.demo.services.ItemServiceDiskImpl;
import java.util.List;
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

  private final ItemServiceDiskImpl itemServiceDisk;
  private final LoggingServiceActivator loggingServiceActivator;

  @Autowired
  public IntegrationConfig(ItemServiceDiskImpl itemServiceDisk,
      LoggingServiceActivator loggingServiceActivator) {
    this.itemServiceDisk = itemServiceDisk;
    this.loggingServiceActivator = loggingServiceActivator;
  }

  @Bean(name = Names.INPUT_CHANNEL)
  public DirectChannel inputChannel() {
    return MessageChannels.direct(Names.INPUT_CHANNEL).get();
  }

  @Bean(name = Names.OUTPUT_CHANNEL)
  public DirectChannel outputChannel() {
    return MessageChannels.direct(Names.OUTPUT_CHANNEL).get();
  }

  @Bean(name = Names.ITEM_ROUTER_CHANNEL)
  public DirectChannel itemRouterChannel() {
    return MessageChannels.direct(Names.ITEM_ROUTER_CHANNEL).get();
  }

  @Bean(name = Names.GET_ITEMS_CHANNEL)
  public DirectChannel getItemsChannel() {
    return MessageChannels.direct(Names.GET_ITEMS_CHANNEL).get();
  }

  @Bean(name = Names.GET_ITEM_BY_ID_CHANNEL)
  public DirectChannel getItemByIdChannel() {
    return MessageChannels.direct(Names.GET_ITEM_BY_ID_CHANNEL).get();
  }

  @Bean(name = Names.ADD_NEW_ITEM_CHANNEL)
  public DirectChannel addNewItemChannel() {
    return MessageChannels.direct(Names.ADD_NEW_ITEM_CHANNEL).get();
  }

  @Bean(name = Names.UPDATE_ITEM_BY_ID_CHANNEL)
  public DirectChannel updateItemByIdChannel() {
    return MessageChannels.direct(Names.UPDATE_ITEM_BY_ID_CHANNEL).get();
  }

  @Bean(name = Names.DELETE_ITEM_BY_ID_CHANNEL)
  public DirectChannel deleteItemByIdChannel() {
    return MessageChannels.direct(Names.DELETE_ITEM_BY_ID_CHANNEL).get();
  }

  @Bean(name = Names.LOG_CHANNEL)
  public DirectChannel logChannel() {
    return MessageChannels.direct(Names.LOG_CHANNEL).get();
  }

  @Bean
  public IntegrationFlow itemFlow() {
    return IntegrationFlows.from(Names.INPUT_CHANNEL)
        .handle(new ServiceActivatingHandler(loggingServiceActivator))
        .channel(Names.ITEM_ROUTER_CHANNEL)
        .get();
  }

  @ServiceActivator(inputChannel = Names.ITEM_ROUTER_CHANNEL)
  @Bean
  public HeaderValueRouter itemRouter() {
    HeaderValueRouter itemRouter = new HeaderValueRouter(Names.API_HEADER_KEY);
    itemRouter.setChannelMapping(Names.API_HEADER_VALUE_GET_ITEMS, Names.GET_ITEMS_CHANNEL);
    itemRouter.setChannelMapping(Names.API_HEADER_VALUE_GET_ITEM_BY_ID,
        Names.GET_ITEM_BY_ID_CHANNEL);
    itemRouter.setChannelMapping(Names.API_HEADER_VALUE_ADD_NEW_ITEM, Names.ADD_NEW_ITEM_CHANNEL);
    itemRouter.setChannelMapping(Names.API_HEADER_VALUE_UPDATE_ITEM_BY_ID,
        Names.UPDATE_ITEM_BY_ID_CHANNEL);
    itemRouter.setChannelMapping(Names.API_HEADER_VALUE_DELETE_ITEM_BY_ID,
        Names.DELETE_ITEM_BY_ID_CHANNEL);
    return itemRouter;
  }

  @ServiceActivator(inputChannel = Names.GET_ITEMS_CHANNEL)
  public Message<List<Item>> getItemsServiceActivator(Message<String> request)
      throws MessagingException {
    return MessageBuilder
        .withPayload(itemServiceDisk.getItems(request.getPayload()))
        .copyHeaders(request.getHeaders())
        .build();
  }

  @ServiceActivator(inputChannel = Names.GET_ITEM_BY_ID_CHANNEL)
  public Message<Item> getItemByIdServiceActivator(Message<String> request)
      throws MessagingException {

    Item searchResult = itemServiceDisk.getItemById(request.getPayload());

    if (searchResult == null) {
      return MessageBuilder
          .withPayload(
              new Item(
                  "unreal",
                  -1,
                  "The item you are looking for does not exist"
              )
          )
          .copyHeaders(request.getHeaders())
          .build();
    } else {
      return MessageBuilder
          .withPayload(searchResult)
          .copyHeaders(request.getHeaders())
          .build();
    }

  }

  @ServiceActivator(inputChannel = Names.ADD_NEW_ITEM_CHANNEL)
  public Message<String> addNewItemServiceActivator(Message<Item> request)
      throws MessagingException {
    return MessageBuilder
        .withPayload(itemServiceDisk.addNewItem(request.getPayload()))
        .copyHeaders(request.getHeaders())
        .build();
  }

  @ServiceActivator(inputChannel = Names.UPDATE_ITEM_BY_ID_CHANNEL)
  public Message<String> updateItemByIdServiceActivator(Message<Item> request)
      throws MessagingException {
    return MessageBuilder
        .withPayload(itemServiceDisk.updateItemById(request.getPayload()))
        .copyHeaders(request.getHeaders())
        .build();
  }

  @ServiceActivator(inputChannel = Names.DELETE_ITEM_BY_ID_CHANNEL)
  public Message<String> deleteItemByIdServiceActivator(Message<String> request)
      throws MessagingException {
    return MessageBuilder
        .withPayload(itemServiceDisk.deleteItemById(request.getPayload()))
        .copyHeaders(request.getHeaders())
        .build();
  }

}
