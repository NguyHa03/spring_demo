package com.spring.demo.controllers;

import com.spring.demo.models.Item;
import java.util.Arrays;
import java.util.List;
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
public class DemoController {

  @GetMapping(path = "/items")
  public List<Item> getItems(@RequestParam(required = false) String id) {
    return Arrays.asList(new Item());
  }

  @GetMapping(path = "/item")
  public Item getItemById(@RequestParam String id) {
    return new Item();
  }

  @PostMapping(path = "/item")
  public void addNewItem(@RequestBody Item item) {
    System.out.println("add new item here");
  }

  @PutMapping(path = "/item")
  public void updateItemById(@RequestParam String id, @RequestBody Item item) {
    System.out.println("update item here");
  }

  @DeleteMapping(path = "/item")
  public void deleteItemById(@RequestParam String id) {
    System.out.println("delete item here");
  }
}
