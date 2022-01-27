package com.spring.demo.services;

import com.spring.demo.configs.Names;
import com.spring.demo.models.Item;
import java.util.List;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway
public interface ItemGateway {

  @Gateway(requestChannel = Names.INPUT_CHANNEL, replyChannel = Names.OUTPUT_CHANNEL)
  List<Item> getItems(@Header(Names.API_HEADER_KEY) String apiHeaderValue, String id);

  @Gateway(requestChannel = Names.INPUT_CHANNEL, replyChannel = Names.OUTPUT_CHANNEL)
  Item getItemById(@Header(Names.API_HEADER_KEY) String apiHeaderValue, String id);

  @Gateway(requestChannel = Names.INPUT_CHANNEL, replyChannel = Names.OUTPUT_CHANNEL)
  String addNewItem(@Header(Names.API_HEADER_KEY) String apiHeaderValue, Item item);

  @Gateway(requestChannel = Names.INPUT_CHANNEL, replyChannel = Names.OUTPUT_CHANNEL)
  String updateItemById(@Header(Names.API_HEADER_KEY) String apiHeaderValue, Item item);

  @Gateway(requestChannel = Names.INPUT_CHANNEL, replyChannel = Names.OUTPUT_CHANNEL)
  String deleteItemById(@Header(Names.API_HEADER_KEY) String apiHeaderValue, String id);

}
