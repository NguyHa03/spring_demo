package com.spring.demo.services;

import com.spring.demo.configs.ApiResponseMessage;
import com.spring.demo.models.Item;
import com.spring.demo.repos.ItemDiskRepo;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceDiskImpl implements ItemService {

  private final ItemDiskRepo itemDiskRepo;

  @Autowired
  public ItemServiceDiskImpl(ItemDiskRepo itemDiskRepo) {
    this.itemDiskRepo = itemDiskRepo;
  }

  @Override
  public List<Item> getItems(String id) {
    if (!StringUtils.isEmpty(id)) {
      return itemDiskRepo.searchForItems(id);
    } else {
      return itemDiskRepo.findAll(Sort.by("id").ascending());
    }
  }

  @Override
  public Item getItemById(String id) {
    if (id != null) {
      return itemDiskRepo.findById(id).orElse(null);
    } else {
      return null;
    }
  }

  @Override
  public ApiResponseMessage addNewItem(Item incomingItem) {
    if (itemDiskRepo.findItemByName(incomingItem.getName()).isEmpty()) {
      Item newItem = new Item(incomingItem.getAmount(), incomingItem.getName());
      itemDiskRepo.save(newItem);
      return new ApiResponseMessage("Successfully added item.");
    } else {
      return new ApiResponseMessage("Item with name <" + incomingItem.getName()
          + "> already exists in database.");
    }
  }

  @Override
  public ApiResponseMessage updateItemById(Item pendingItem) {
    Item existingItem = getItemById(pendingItem.getId());
    if (existingItem == null) {
      return new ApiResponseMessage("Item with ID <" + pendingItem.getId()
          + "> does not exist in database.");
    } else {
      existingItem.setAmount(pendingItem.getAmount());
      existingItem.setName(pendingItem.getName());
      itemDiskRepo.save(existingItem);
      return new ApiResponseMessage("Successfully updated item.");
    }
  }

  @Override
  public ApiResponseMessage deleteItemById(String id) {
    Item existingItem = getItemById(id);
    if (existingItem == null) {
      return new ApiResponseMessage("Item with ID <" + id + "> does not exist in database.");
    } else {
      itemDiskRepo.deleteById(id);
      return new ApiResponseMessage("Successfully deleted item.");
    }
  }

}
