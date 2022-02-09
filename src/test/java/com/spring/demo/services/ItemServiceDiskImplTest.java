// this class is heavily commented to help with learning.

package com.spring.demo.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spring.demo.configs.ApiResponseMessage;
import com.spring.demo.models.Item;
import com.spring.demo.repos.ItemDiskRepo;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

@ExtendWith({MockitoExtension.class})
class ItemServiceDiskImplTest {

  public static final String DUMMY_ITEM_ID = "dummy id";
  public static final String DUMMY_ITEM_ID_EMPTY = "";
  public static final Sort SORT_BY_ID_ASCENDING = Sort.by("id").ascending();

  // this is the Object Under Test (OUT), which is a real object and not a mock object.
  // @InjectMocks lets Mockito know that we want to inject mock objects into it.
  @InjectMocks
  private ItemServiceDiskImpl itemServiceDiskObjectUnderTest;

  // these are mock objects (or dependencies). not real. used for testing only.
  // we need to mock the OUT's dependencies to maintain atomicity in our tests.
  @Mock
  private ItemDiskRepo itemDiskRepoMockObject;
  @Mock
  private Item itemMockObject;
  @Mock
  private List<Item> itemListMockObject;
  @Mock
  private Item newItemMockObject;
  @Mock
  private Optional<Item> existingItemMockObject;

  // According to https://www.baeldung.com/mockito-argumentcaptor :
  // "ArgumentCaptor allows us to capture an argument passed to a method in order to inspect it.
  // This is especially useful when we can't access the argument outside of the method we'd like to test."
  @Captor
  private ArgumentCaptor<Item> itemArgumentCaptor;
  @Captor
  private ArgumentCaptor<String> itemIdArgumentCaptor;
  @Captor
  private ArgumentCaptor<Sort> findAllArgumentCaptor;

  @Test
  public void getItemsShouldSearchForItemsIfItemIdNotEmpty() {

    when(itemDiskRepoMockObject.searchForItems(DUMMY_ITEM_ID)).thenReturn(itemListMockObject);

    List<Item> actualItemList = itemServiceDiskObjectUnderTest.getItems(DUMMY_ITEM_ID);

    verify(itemDiskRepoMockObject).searchForItems(itemIdArgumentCaptor.capture());

    String itemIdArgumentCaptorValue = itemIdArgumentCaptor.getValue();
    assertThat(itemIdArgumentCaptorValue).isEqualTo(DUMMY_ITEM_ID);

    assertThat(actualItemList).isEqualTo(itemListMockObject);

  }

  @Test
  public void getItemsShouldFindAllSortByIdAscendingIfItemIdEmpty() {

    when(itemDiskRepoMockObject.findAll(SORT_BY_ID_ASCENDING)).thenReturn(itemListMockObject);

    List<Item> actualItemList = itemServiceDiskObjectUnderTest.getItems(DUMMY_ITEM_ID_EMPTY);

    verify(itemDiskRepoMockObject).findAll(findAllArgumentCaptor.capture());

    Sort findAllArgumentCaptorValue = findAllArgumentCaptor.getValue();
    assertThat(findAllArgumentCaptorValue).isEqualTo(SORT_BY_ID_ASCENDING);

    assertThat(actualItemList).isEqualTo(itemListMockObject);

  }

  @Test
  public void getItemByIdShouldReturnItemIfIdNotNullAndItemFound() {

    when(itemDiskRepoMockObject.findById(DUMMY_ITEM_ID)).thenReturn(
        Optional.of(itemMockObject));

    Item actualItem = itemServiceDiskObjectUnderTest.getItemById(DUMMY_ITEM_ID);

    verify(itemDiskRepoMockObject).findById(itemIdArgumentCaptor.capture());

    String itemIdArgumentCaptorValue = itemIdArgumentCaptor.getValue();
    assertThat(itemIdArgumentCaptorValue).isEqualTo(DUMMY_ITEM_ID);

    assertThat(actualItem).isEqualTo(itemMockObject);

  }

  @Test
  public void getItemByIdShouldReturnNullIfIdNotNullAndItemNotFound() {

    when(itemDiskRepoMockObject.findById(DUMMY_ITEM_ID)).thenReturn(Optional.empty());

    Item actualItem = itemServiceDiskObjectUnderTest.getItemById(DUMMY_ITEM_ID);

    verify(itemDiskRepoMockObject).findById(itemIdArgumentCaptor.capture());

    String itemIdArgumentCaptorValue = itemIdArgumentCaptor.getValue();
    assertThat(itemIdArgumentCaptorValue).isEqualTo(DUMMY_ITEM_ID);

    assertThat(actualItem).isNull();

  }

  @Test
  public void getItemByIdShouldReturnNullIfIdNull() {

    Item actualItem = itemServiceDiskObjectUnderTest.getItemById(null);

    assertThat(actualItem).isNull();

  }

  // long method name, but hopefully it's clear.
  @Test
  public void addNewItemShouldSaveNewItemThenReturnConfirmationMessageWhenNewItemNameNotFoundInDatabase() {

    // specifying our mock objects' expected behaviors & outputs.
    when(itemDiskRepoMockObject.findItemByName(newItemMockObject.getName())).thenReturn(
        existingItemMockObject);
    when(existingItemMockObject.isEmpty()).thenReturn(true);

    // invoking the Method Under Test (MUT),...
    // ...then use a ApiResponseMessage object to carry its output.
    // in other words, this is where we run the test.
    ApiResponseMessage actualApiResponseMessage = itemServiceDiskObjectUnderTest.addNewItem(
        newItemMockObject);

    // now we compare our actual behaviors/outputs against our expected behaviors/outputs.

    // verifying that itemDiskRepoMockObject.save() was properly invoked by the MUT...
    // ...while also capturing its input argument.
    // itemDiskRepoMockObject.save() is a (mocked) Spring repo-level method, so this will...
    // ...allow us to check if the MUT is passing our expected argument to the (mocked) repo.
    // in short, this is a behavior check.
    verify(itemDiskRepoMockObject).save(itemArgumentCaptor.capture());

    // comparing the captured (actual) input argument against the expected input argument.
    Item itemArgumentCaptorValue = itemArgumentCaptor.getValue();
    assertThat(itemArgumentCaptorValue.getAmount()).isEqualTo(newItemMockObject.getAmount());
    assertThat(itemArgumentCaptorValue.getName()).isEqualTo(newItemMockObject.getName());

    // comparing the value of the MUT's output (actual output) against our expected output.
    assertThat(actualApiResponseMessage.getMessage()).isEqualTo("Successfully added item.");

  }

  @Test
  public void addNewItemShouldReturnErrorMessageWhenNewItemNameFoundInDatabase() {

    when(itemDiskRepoMockObject.findItemByName(newItemMockObject.getName())).thenReturn(
        existingItemMockObject);
    when(existingItemMockObject.isEmpty()).thenReturn(false);

    ApiResponseMessage actualApiResponseMessage = itemServiceDiskObjectUnderTest.addNewItem(
        newItemMockObject);

    assertThat(actualApiResponseMessage.getMessage()).isEqualTo(
        "Item with name <" + newItemMockObject.getName()
            + "> already exists in database.");

  }

  @Test
  public void updateItemByIdShouldReturnErrorMessageWhenItemNotFoundInDatabase() {

    when(itemMockObject.getId()).thenReturn(DUMMY_ITEM_ID);
    when(itemDiskRepoMockObject.findById(itemMockObject.getId())).thenReturn(Optional.empty());

    ApiResponseMessage actualApiResponseMessage = itemServiceDiskObjectUnderTest.updateItemById(
        itemMockObject);

    assertThat(actualApiResponseMessage.getMessage()).isEqualTo(
        "Item with ID <" + itemMockObject.getId()
            + "> does not exist in database.");
  }

  @Test
  public void updateItemByIdShouldUpdateItemThenReturnConfirmationMessageWhenItemFoundInDatabase() {

    when(itemMockObject.getId()).thenReturn(DUMMY_ITEM_ID);
    when(itemDiskRepoMockObject.findById(itemMockObject.getId())).thenReturn(
        Optional.of(itemMockObject));

    ApiResponseMessage actualApiResponseMessage = itemServiceDiskObjectUnderTest.updateItemById(
        itemMockObject);

    verify(itemMockObject).setAmount(itemMockObject.getAmount());
    verify(itemMockObject).setName(itemMockObject.getName());
    verify(itemDiskRepoMockObject).save(itemMockObject);
    assertThat(actualApiResponseMessage.getMessage()).isEqualTo("Successfully updated item.");

  }

  @Test
  public void deleteItemByIdShouldReturnErrorMessageWhenItemNotFoundInDatabase() {

    when(itemDiskRepoMockObject.findById(DUMMY_ITEM_ID)).thenReturn(Optional.empty());

    ApiResponseMessage actualApiResponseMessage = itemServiceDiskObjectUnderTest.deleteItemById(
        DUMMY_ITEM_ID);

    assertThat(actualApiResponseMessage.getMessage()).isEqualTo(
        "Item with ID <" + DUMMY_ITEM_ID + "> does not exist in database.");

  }

  @Test
  public void deleteItemByIdShouldDeleteItemAndReturnConfirmationMessageWhenItemFoundInDatabase() {

    when(itemDiskRepoMockObject.findById(DUMMY_ITEM_ID)).thenReturn(Optional.of(itemMockObject));

    ApiResponseMessage actualApiResponseMessage = itemServiceDiskObjectUnderTest.deleteItemById(
        DUMMY_ITEM_ID);

    verify(itemDiskRepoMockObject).deleteById(DUMMY_ITEM_ID);

    assertThat(actualApiResponseMessage.getMessage()).isEqualTo("Successfully deleted item.");

  }

}
