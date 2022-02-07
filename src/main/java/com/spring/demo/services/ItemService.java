package com.spring.demo.services;

import com.spring.demo.configs.Message;
import com.spring.demo.models.Item;
import java.util.List;

public interface ItemService {

  List<Item> getItems(String id);

  Item getItemById(String id);

  Message addNewItem(Item item);

  Message updateItemById(Item item);

  Message deleteItemById(String id);

}
