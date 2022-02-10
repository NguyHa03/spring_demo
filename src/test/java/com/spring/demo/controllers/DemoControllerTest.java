package com.spring.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spring.demo.configs.ApiResponseMessage;
import com.spring.demo.configs.ItemChannels;
import com.spring.demo.models.Item;
import com.spring.demo.services.ItemGateway;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DemoControllerTest {

  public static final String DUMMY_ITEM_ID = "dummy id";
  public static final String DUMMY_ITEM_ID_EMPTY = "";

  @InjectMocks
  private DemoController demoControllerObjectUnderTest;

  @Mock
  private ItemGateway itemGatewayMockObject;
  @Mock
  private List<Item> itemListMockObject;
  @Mock
  private Item itemMockObject;
  @Mock
  private ApiResponseMessage apiResponseMessageMockObject;


  @Captor
  private ArgumentCaptor<String> itemChannelsArgumentCaptor;
  @Captor
  private ArgumentCaptor<String> itemIdArgumentCaptor;
  @Captor
  private ArgumentCaptor<Item> itemArgumentCaptor;

  @Test
  public void getItemsShouldReturnItemListWhenItemIdNotNull() {

    when(itemGatewayMockObject.getItems(ItemChannels.API_HEADER_VALUE_GET_ITEMS,
        DUMMY_ITEM_ID)).thenReturn(itemListMockObject);

    List<Item> actualItemList = demoControllerObjectUnderTest.getItems(DUMMY_ITEM_ID);

    verify(itemGatewayMockObject).getItems(itemChannelsArgumentCaptor.capture(),
        itemIdArgumentCaptor.capture());

    String itemChannelsArgumentCaptorValue = itemChannelsArgumentCaptor.getValue();
    assertThat(itemChannelsArgumentCaptorValue).isEqualTo(ItemChannels.API_HEADER_VALUE_GET_ITEMS);

    String itemIdArgumentCaptorValue = itemIdArgumentCaptor.getValue();
    assertThat(itemIdArgumentCaptorValue).isEqualTo(DUMMY_ITEM_ID);

    assertThat(actualItemList).isEqualTo(itemListMockObject);

  }

  @Test
  public void getItemsShouldReturnItemListWhenItemIdNull() {

    when(itemGatewayMockObject.getItems(ItemChannels.API_HEADER_VALUE_GET_ITEMS,
        DUMMY_ITEM_ID_EMPTY)).thenReturn(itemListMockObject);

    List<Item> actualItemList = demoControllerObjectUnderTest.getItems(null);

    verify(itemGatewayMockObject).getItems(itemChannelsArgumentCaptor.capture(),
        itemIdArgumentCaptor.capture());

    String itemChannelsArgumentCaptorValue = itemChannelsArgumentCaptor.getValue();
    assertThat(itemChannelsArgumentCaptorValue).isEqualTo(ItemChannels.API_HEADER_VALUE_GET_ITEMS);

    String itemIdArgumentCaptorValue = itemIdArgumentCaptor.getValue();
    assertThat(itemIdArgumentCaptorValue).isEqualTo(DUMMY_ITEM_ID_EMPTY);

    assertThat(actualItemList).isEqualTo(itemListMockObject);

  }

  @Test
  public void getItemByIdShouldReturnItemWhenItemIdNotNull() {

    when(itemGatewayMockObject.getItemById(ItemChannels.API_HEADER_VALUE_GET_ITEM_BY_ID,
        DUMMY_ITEM_ID)).thenReturn(itemMockObject);

    Item actualItem = demoControllerObjectUnderTest.getItemById(DUMMY_ITEM_ID);

    verify(itemGatewayMockObject).getItemById(itemChannelsArgumentCaptor.capture(),
        itemIdArgumentCaptor.capture());

    String itemChannelsArgumentCaptorValue = itemChannelsArgumentCaptor.getValue();
    assertThat(itemChannelsArgumentCaptorValue).isEqualTo(
        ItemChannels.API_HEADER_VALUE_GET_ITEM_BY_ID);

    String itemIdArgumentCaptorValue = itemIdArgumentCaptor.getValue();
    assertThat(itemIdArgumentCaptorValue).isEqualTo(DUMMY_ITEM_ID);

    assertThat(actualItem).isEqualTo(itemMockObject);

  }

  @Test
  public void getItemByIdShouldReturnItemWhenItemIdNull() {

    when(itemGatewayMockObject.getItemById(ItemChannels.API_HEADER_VALUE_GET_ITEM_BY_ID,
        DUMMY_ITEM_ID_EMPTY)).thenReturn(itemMockObject);

    Item actualItem = demoControllerObjectUnderTest.getItemById(null);

    verify(itemGatewayMockObject).getItemById(itemChannelsArgumentCaptor.capture(),
        itemIdArgumentCaptor.capture());

    String itemChannelsArgumentCaptorValue = itemChannelsArgumentCaptor.getValue();
    assertThat(itemChannelsArgumentCaptorValue).isEqualTo(
        ItemChannels.API_HEADER_VALUE_GET_ITEM_BY_ID);

    String itemIdArgumentCaptorValue = itemIdArgumentCaptor.getValue();
    assertThat(itemIdArgumentCaptorValue).isEqualTo(DUMMY_ITEM_ID_EMPTY);

    assertThat(actualItem).isEqualTo(itemMockObject);

  }

  @Test
  public void addNewItemShouldReturnApiResponseMessage() {

    when(itemGatewayMockObject.addNewItem(ItemChannels.API_HEADER_VALUE_ADD_NEW_ITEM,
        itemMockObject)).thenReturn(apiResponseMessageMockObject);

    ApiResponseMessage actualApiResponseMessage = demoControllerObjectUnderTest.addNewItem(
        itemMockObject);

    verify(itemGatewayMockObject).addNewItem(itemChannelsArgumentCaptor.capture(),
        itemArgumentCaptor.capture());

    String itemChannelsArgumentCaptorValue = itemChannelsArgumentCaptor.getValue();
    assertThat(itemChannelsArgumentCaptorValue).isEqualTo(
        ItemChannels.API_HEADER_VALUE_ADD_NEW_ITEM);

    Item itemArgumentCaptorValue = itemArgumentCaptor.getValue();
    assertThat(itemArgumentCaptorValue).isEqualTo(itemMockObject);

    assertThat(actualApiResponseMessage).isEqualTo(apiResponseMessageMockObject);

  }

  @Test
  public void updateItemByIdShouldReturnApiResponseMessage() {

    when(itemGatewayMockObject.updateItemById(ItemChannels.API_HEADER_VALUE_UPDATE_ITEM_BY_ID,
        itemMockObject)).thenReturn(apiResponseMessageMockObject);

    ApiResponseMessage actualApiResponseMessage = demoControllerObjectUnderTest.updateItemById(
        itemMockObject);

    verify(itemGatewayMockObject).updateItemById(itemChannelsArgumentCaptor.capture(),
        itemArgumentCaptor.capture());

    String itemChannelsArgumentCaptorValue = itemChannelsArgumentCaptor.getValue();
    assertThat(itemChannelsArgumentCaptorValue).isEqualTo(
        ItemChannels.API_HEADER_VALUE_UPDATE_ITEM_BY_ID);

    Item itemArgumentCaptorValue = itemArgumentCaptor.getValue();
    assertThat(itemArgumentCaptorValue).isEqualTo(itemMockObject);

    assertThat(actualApiResponseMessage).isEqualTo(apiResponseMessageMockObject);

  }

  @Test
  public void deleteItemByIdShouldReturnApiResponseMessage() {

    when(itemGatewayMockObject.deleteItemById(ItemChannels.API_HEADER_VALUE_DELETE_ITEM_BY_ID,
        DUMMY_ITEM_ID)).thenReturn(apiResponseMessageMockObject);

    ApiResponseMessage actualApiResponseMessage = demoControllerObjectUnderTest.deleteItemById(
        DUMMY_ITEM_ID);

    verify(itemGatewayMockObject).deleteItemById(itemChannelsArgumentCaptor.capture(),
        itemIdArgumentCaptor.capture());

    String itemChannelsArgumentCaptorValue = itemChannelsArgumentCaptor.getValue();
    assertThat(itemChannelsArgumentCaptorValue).isEqualTo(
        ItemChannels.API_HEADER_VALUE_DELETE_ITEM_BY_ID);

    String itemIdArgumentCaptorValue = itemIdArgumentCaptor.getValue();
    assertThat(itemIdArgumentCaptorValue).isEqualTo(DUMMY_ITEM_ID);

    assertThat(actualApiResponseMessage).isEqualTo(apiResponseMessageMockObject);

  }

}
