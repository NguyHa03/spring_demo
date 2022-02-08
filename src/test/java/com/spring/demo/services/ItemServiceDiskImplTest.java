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

  public static final String FAKE_ITEM_ID = "fake id";
  public static final String FAKE_ITEM_ID_EMPTY = "";
  public static final int FAKE_ITEM_AMOUNT = -1;
  public static final String FAKE_ITEM_NAME = "fake name";
  public static final Sort SORT_BY_ID_ASCENDING = Sort.by("id").ascending();

  // this is the Object Under Test (OUT), which is a real object and not a mock object.
  // @InjectMocks lets Mockito know that we want to inject mock objects into it.
  @InjectMocks
  private ItemServiceDiskImpl itemServiceDiskOUT;

  // these are mock objects (or dependencies). not real. used for testing only.
  // we need to mock the OUT's dependencies to maintain atomicity in our tests.
  @Mock
  private ItemDiskRepo itemDiskRepoMockObject;
  @Mock
  private Item itemMockObject = new Item(FAKE_ITEM_ID, FAKE_ITEM_AMOUNT, FAKE_ITEM_NAME);
  @Mock
  private List<Item> itemListMockObject = List.of(itemMockObject);
  @Mock
  private Item incomingItemMockObject;
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
  void getItemsShouldSearchForItemsIfItemIdNotEmpty() {

    when(itemDiskRepoMockObject.searchForItems(FAKE_ITEM_ID)).thenReturn(itemListMockObject);

    List<Item> methodUnderTestOutput = itemServiceDiskOUT.getItems(FAKE_ITEM_ID);

    verify(itemDiskRepoMockObject).searchForItems(itemIdArgumentCaptor.capture());

    String capturedArgument = itemIdArgumentCaptor.getValue();
    assertThat(capturedArgument).isEqualTo(FAKE_ITEM_ID);

    assertThat(methodUnderTestOutput).isEqualTo(itemListMockObject);

  }

  @Test
  void getItemsShouldFindAllSortByIdAscendingIfItemIdEmpty() {

    when(itemDiskRepoMockObject.findAll(SORT_BY_ID_ASCENDING)).thenReturn(itemListMockObject);

    List<Item> methodUnderTestOutput = itemServiceDiskOUT.getItems(FAKE_ITEM_ID_EMPTY);

    verify(itemDiskRepoMockObject).findAll(findAllArgumentCaptor.capture());

    Sort capturedArgument = findAllArgumentCaptor.getValue();
    assertThat(capturedArgument).isEqualTo(SORT_BY_ID_ASCENDING);

    assertThat(methodUnderTestOutput).isEqualTo(itemListMockObject);

  }

  @Test
  void getItemByIdShouldReturnItemIfIdNotNullAndItemFound() {

    when(itemDiskRepoMockObject.findById(FAKE_ITEM_ID)).thenReturn(
        Optional.ofNullable(itemMockObject));

    Item methodUnderTestOutput = itemServiceDiskOUT.getItemById(FAKE_ITEM_ID);

    verify(itemDiskRepoMockObject).findById(itemIdArgumentCaptor.capture());

    String capturedArgument = itemIdArgumentCaptor.getValue();
    assertThat(capturedArgument).isEqualTo(FAKE_ITEM_ID);

    assertThat(methodUnderTestOutput).isEqualTo(itemMockObject);

  }

  @Test
  void getItemByIdShouldReturnNullIfIdNotNullAndItemNotFound() {

    when(itemDiskRepoMockObject.findById(FAKE_ITEM_ID)).thenReturn(Optional.empty());

    Item methodUnderTestOutput = itemServiceDiskOUT.getItemById(FAKE_ITEM_ID);

    verify(itemDiskRepoMockObject).findById(itemIdArgumentCaptor.capture());

    String capturedArgument = itemIdArgumentCaptor.getValue();
    assertThat(capturedArgument).isEqualTo(FAKE_ITEM_ID);

    assertThat(methodUnderTestOutput).isNull();

  }

  @Test
  void getItemByIdShouldReturnNullIfIdNull() {

    Item methodUnderTestOutput = itemServiceDiskOUT.getItemById(null);

    assertThat(methodUnderTestOutput).isNull();

  }

  // long method name, but hopefully it's clear.
  @Test
  void addNewItemShouldSaveNewItemAndReturnConfirmationMessageWhenNewItemNameNotFoundInDatabase() {

    // specifying our mock objects' expected behaviors & outputs.
    when(itemDiskRepoMockObject.findItemByName(incomingItemMockObject.getName())).thenReturn(
        existingItemMockObject);
    when(existingItemMockObject.isEmpty()).thenReturn(true);

    // invoking the Method Under Test (MUT),...
    // ...then use a ApiResponseMessage object to carry its output.
    // in other words, this is where we run the test.
    ApiResponseMessage methodUnderTestOutput = itemServiceDiskOUT.addNewItem(
        incomingItemMockObject);

    // now we compare our actual behaviors/outputs against our expected behaviors/outputs.

    // verifying that itemDiskRepoMockObject.save() was properly invoked by the MUT...
    // ...while also capturing its input argument.
    // itemDiskRepoMockObject.save() is a (mocked) Spring repo-level method, so this will...
    // ...allow us to check if the MUT is passing our expected argument to the (mocked) repo.
    // in short, this is a behavior check.
    verify(itemDiskRepoMockObject).save(itemArgumentCaptor.capture());

    // comparing the captured (actual) input argument against the expected input argument.
    Item capturedArgument = itemArgumentCaptor.getValue();
    assertThat(capturedArgument.getAmount()).isEqualTo(incomingItemMockObject.getAmount());
    assertThat(capturedArgument.getName()).isEqualTo(incomingItemMockObject.getName());

    // comparing the value of the MUT's output (actual output) against our expected output.
    assertThat(methodUnderTestOutput.getMessage()).isEqualTo("Successfully added item.");

  }

  @Test
  void addNewItemShouldReturnErrorMessageWhenNewItemNameFoundInDatabase() {

    when(itemDiskRepoMockObject.findItemByName(incomingItemMockObject.getName())).thenReturn(
        existingItemMockObject);
    when(existingItemMockObject.isEmpty()).thenReturn(false);

    ApiResponseMessage methodUnderTestOutput = itemServiceDiskOUT.addNewItem(
        incomingItemMockObject);

    assertThat(methodUnderTestOutput.getMessage()).isEqualTo(
        "Item with name <" + incomingItemMockObject.getName()
            + "> already exists in database.");

  }

}