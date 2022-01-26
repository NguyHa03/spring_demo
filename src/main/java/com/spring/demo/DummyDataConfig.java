package com.spring.demo;

import com.spring.demo.models.Item;
import com.spring.demo.repos.ItemDiskRepo;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyDataConfig {

  // This is how we populate the database with dummy data without using SQL scripts
  @Bean
  CommandLineRunner dummyDataCommandLineRunner(ItemDiskRepo itemDiskRepo) {

    return args -> {

      Item item0 = new Item(100, "Item Zero");
      Item item1 = new Item(99, "Item One");
      Item item2 = new Item(98, "Item Two");
      Item item3 = new Item(97, "Item Three");
      Item item4 = new Item(96, "Item Four");

      itemDiskRepo.saveAll(List.of(item0, item1, item2, item3, item4));

    };

  }

}
