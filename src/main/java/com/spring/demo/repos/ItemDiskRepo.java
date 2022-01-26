package com.spring.demo.repos;

import com.spring.demo.models.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ItemDiskRepo extends JpaRepository<Item, String> {

  @Query("SELECT item FROM Item item WHERE lower(CONCAT(item.id, '')) LIKE %?1%")
  List<Item> searchForItems(String id);

  Optional<Item> findItemByName(String name);

}
