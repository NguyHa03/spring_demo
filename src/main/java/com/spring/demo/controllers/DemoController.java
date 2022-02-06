package com.spring.demo.controllers;

import com.spring.demo.configs.ItemChannels;
import com.spring.demo.configs.Message;
import com.spring.demo.models.Item;
import com.spring.demo.services.ItemGateway;
import com.spring.demo.services.ItemServiceDiskImpl;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DemoController {

  private final ItemGateway itemGateway;

  @Autowired
  public DemoController(ItemServiceDiskImpl itemServiceDisk, ItemGateway itemGateway) {
    this.itemGateway = itemGateway;
  }

  @GetMapping(path = "/items")
  public List<Item> getItems(@RequestParam(required = false) String id) {
    if (StringUtils.isEmpty(id)) {
      id = "";
    }
    return itemGateway.getItems(ItemChannels.API_HEADER_VALUE_GET_ITEMS, id);
  }

  @GetMapping(path = "/item")
  public Item getItemById(@RequestParam String id) {
    return itemGateway.getItemById(ItemChannels.API_HEADER_VALUE_GET_ITEM_BY_ID, id);
  }

  @PostMapping(path = "/item")
  public Message addNewItem(@RequestBody Item item) {
    return itemGateway.addNewItem(ItemChannels.API_HEADER_VALUE_ADD_NEW_ITEM, item);
  }

  @PutMapping(path = "/item")
  public Message updateItemById(@RequestBody Item item) {
    return itemGateway.updateItemById(ItemChannels.API_HEADER_VALUE_UPDATE_ITEM_BY_ID, item);
  }

  @DeleteMapping(path = "/item")
  public Message deleteItemById(@RequestParam String id) {
    return itemGateway.deleteItemById(ItemChannels.API_HEADER_VALUE_DELETE_ITEM_BY_ID, id);
  }

}
