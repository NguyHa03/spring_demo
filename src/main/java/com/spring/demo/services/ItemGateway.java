package com.spring.demo.services;

import com.spring.demo.configs.ItemChannels;
import com.spring.demo.configs.Message;
import com.spring.demo.models.Item;
import java.util.List;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway
public interface ItemGateway {

  @Gateway(requestChannel = ItemChannels.INPUT_CHANNEL, replyChannel = ItemChannels.OUTPUT_CHANNEL)
  List<Item> getItems(@Header(ItemChannels.API_HEADER_KEY) String apiHeaderValue, String id);

  @Gateway(requestChannel = ItemChannels.INPUT_CHANNEL, replyChannel = ItemChannels.OUTPUT_CHANNEL)
  Item getItemById(@Header(ItemChannels.API_HEADER_KEY) String apiHeaderValue, String id);

  @Gateway(requestChannel = ItemChannels.INPUT_CHANNEL, replyChannel = ItemChannels.OUTPUT_CHANNEL)
  Message addNewItem(@Header(ItemChannels.API_HEADER_KEY) String apiHeaderValue, Item item);

  @Gateway(requestChannel = ItemChannels.INPUT_CHANNEL, replyChannel = ItemChannels.OUTPUT_CHANNEL)
  Message updateItemById(@Header(ItemChannels.API_HEADER_KEY) String apiHeaderValue, Item item);

  @Gateway(requestChannel = ItemChannels.INPUT_CHANNEL, replyChannel = ItemChannels.OUTPUT_CHANNEL)
  Message deleteItemById(@Header(ItemChannels.API_HEADER_KEY) String apiHeaderValue, String id);

}
