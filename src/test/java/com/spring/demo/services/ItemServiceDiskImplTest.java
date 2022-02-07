package com.spring.demo.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spring.demo.configs.Message;
import com.spring.demo.models.Item;
import com.spring.demo.repos.ItemDiskRepo;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class ItemServiceDiskImplTest {

  @InjectMocks
  private ItemServiceDiskImpl itemServiceDisk;
  @Mock
  private ItemDiskRepo itemDiskRepo;
  @Mock
  private Item item;
  @Mock
  private Optional<Item> optionalItem;
  @Captor
  private ArgumentCaptor<Item> itemArgumentCaptor;

  @Test
  void addNewItemShouldSaveItemWhenItemIsFoundByName() {
    when(itemDiskRepo.findItemByName(item.getName())).thenReturn(optionalItem);
    when(optionalItem.isEmpty()).thenReturn(true);

    Message message = itemServiceDisk.addNewItem(item);

    verify(itemDiskRepo).save(itemArgumentCaptor.capture());
    Item newItem = itemArgumentCaptor.getValue();
    assertThat(newItem.getAmount()).isEqualTo(item.getAmount());
    assertThat(newItem.getName()).isEqualTo(item.getName());
    assertThat(message.getMessage()).isEqualTo("Successfully added item.");

  }
}