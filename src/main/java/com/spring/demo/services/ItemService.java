package com.spring.demo.services;

import com.spring.demo.models.Item;
import java.util.List;

public interface ItemService {

  List<Item> getItems(String id);

  Item getItemById(String id);

  String addNewItem(Item item);

  String updateItemById(String id, Item item);

  String deleteItemById(String id);

}
