package com.spring.demo.configs;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemChannelsTest {

  ItemChannels itemChannels = new ItemChannels();

  @Test
  void itemChannelsValuesShouldMatch() {
    assertThat(itemChannels.INPUT_CHANNEL).isEqualTo("input.channel");
    assertThat(itemChannels.OUTPUT_CHANNEL).isEqualTo("output.channel");
    assertThat(itemChannels.ITEM_ROUTER_CHANNEL).isEqualTo("item.router.channel");

    assertThat(itemChannels.ADD_NEW_ITEM_CHANNEL).isEqualTo("add.new.item.channel");
    assertThat(itemChannels.GET_ITEMS_CHANNEL).isEqualTo("get.items.channel");
    assertThat(itemChannels.GET_ITEM_BY_ID_CHANNEL).isEqualTo("get.item.by.id.channel");
    assertThat(itemChannels.UPDATE_ITEM_BY_ID_CHANNEL).isEqualTo("update.item.by.id.channel");
    assertThat(itemChannels.DELETE_ITEM_BY_ID_CHANNEL).isEqualTo("delete.item.by.id.channel");
    assertThat(itemChannels.LOG_CHANNEL).isEqualTo("log.channel");

    assertThat(itemChannels.API_HEADER_KEY).isEqualTo("apiHeaderKey");
    assertThat(itemChannels.API_HEADER_VALUE_GET_ITEMS).isEqualTo("getItems");
    assertThat(itemChannels.API_HEADER_VALUE_GET_ITEM_BY_ID).isEqualTo("getItemById");
    assertThat(itemChannels.API_HEADER_VALUE_ADD_NEW_ITEM).isEqualTo("addNewItem");
    assertThat(itemChannels.API_HEADER_VALUE_UPDATE_ITEM_BY_ID).isEqualTo("updateItemById");
    assertThat(itemChannels.API_HEADER_VALUE_DELETE_ITEM_BY_ID).isEqualTo("deleteItemById");
  }

}
