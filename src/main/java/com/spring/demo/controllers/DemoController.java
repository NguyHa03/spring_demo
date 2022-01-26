package com.spring.demo.controllers;

import com.spring.demo.models.Item;
import com.spring.demo.services.ItemServiceDiskImpl;
import java.util.List;
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

  private final ItemServiceDiskImpl itemServiceDisk;

  @Autowired
  public DemoController(ItemServiceDiskImpl itemServiceDisk) {
    this.itemServiceDisk = itemServiceDisk;
  }

  @GetMapping(path = "/items")
  public List<Item> getItems(@RequestParam(required = false) String id) {
//    return Arrays.asList(new Item());
    return itemServiceDisk.getItems(id);
  }

  @GetMapping(path = "/item")
  public Item getItemById(@RequestParam String id) {
    return itemServiceDisk.getItemById(id);
  }

  @PostMapping(path = "/item")
  public String addNewItem(@RequestBody Item item) {
    return itemServiceDisk.addNewItem(item);
  }

  @PutMapping(path = "/item")
  public String updateItemById(@RequestParam String id, @RequestBody Item item) {
    return itemServiceDisk.updateItemById(id, item);
  }

  @DeleteMapping(path = "/item")
  public String deleteItemById(@RequestParam String id) {
    return itemServiceDisk.deleteItemById(id);
  }
}
