package com.spring.demo.services;

import com.spring.demo.configs.ApiResponseMessage;
import com.spring.demo.models.Item;
import java.util.List;

public interface ItemService {

  List<Item> getItems(String id);

  Item getItemById(String id);

  ApiResponseMessage addNewItem(Item item);

  ApiResponseMessage updateItemById(Item item);

  ApiResponseMessage deleteItemById(String id);

}
