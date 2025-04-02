package com.ihren.exercise5;

import com.ihren.exercise5.models.Data;
import com.ihren.exercise5.models.Item;
import com.ihren.exercise5.models.Return;
import com.ihren.exercise5.models.Sale;
import com.ihren.exercise5.models.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class Exercise5Test {

    @Spy
    @InjectMocks
    private Exercise5 exercise5;

    private static final String SOME_TYPE = "SOME_TYPE";

    @Test
    @DisplayName("Should return list of items when all filter functions return true")
    void convertReturnsListWhenAllFiltersTrue() {
        //given
        List<Item> items = List.of(
                new Item(new Sale(), "Fuel1", new Return(), new Data("typeA")),
                new Item(null, null, new Return(), new Data(SOME_TYPE)),
                new Item(null, null, new Return(), new Data(null)),
                new Item(null, null, new Return(), new Data("typeB")),
                new Item(new Sale(), null, null, new Data("typeC")),
                new Item(new Sale(), "Fuel2", new Return(), null),
                new Item(null, "Fuel3", new Return(), null),
                new Item(null, null, null, null)
        );
        Transaction transaction = new Transaction(items);
        List<Item> expected = List.of(
                new Item(new Sale(), "Fuel1", new Return(), new Data("typeA")),
                new Item(null, null, new Return(), new Data(SOME_TYPE)),
                new Item(new Sale(), null, null, new Data("typeC")),
                new Item(new Sale(), "Fuel2", new Return(), null),
                new Item(null, "Fuel3", new Return(), null)
        );

        doReturn(items).when(exercise5).filterItems(anyList());
        doReturn(true).when(exercise5).filterSale(any(Sale.class));
        doReturn(true).when(exercise5).isReturnTransaction(transaction);
        doAnswer(invocationOnMock ->
                invocationOnMock.getArgument(0)
        )
        .when(exercise5).createItem(any(Item.class));

        //when
        List<Item> actual = exercise5.convert(transaction);

        //then
        assertEquals(expected, actual);

        verify(exercise5).filterItems(anyList());
        verify(exercise5, times(3)).filterSale(any(Sale.class));
        verify(exercise5, times(4)).isReturnTransaction(transaction);
        verify(exercise5, times(5)).createItem(any(Item.class));
    }

    @Test
    @DisplayName("Should return list of items when filter items return empty list")
    void convertReturnsListWhenFilterItemsEmpty() {
        //given
        List<Item> items = List.of(
                new Item(new Sale(), "Fuel1", new Return(), new Data("typeA")),
                new Item(null, null, new Return(), new Data(SOME_TYPE)),
                new Item(null, null, new Return(), new Data("typeB")),
                new Item(new Sale(), null, null, new Data("typeC")),
                new Item(new Sale(), "Fuel4", new Return(), null),
                new Item(null, "Fuel4", new Return(), null),
                new Item(null, null, null, null)
        );
        Transaction transaction = new Transaction(items);
        int expectedItemCount = 0;

        doReturn(List.of()).when(exercise5).filterItems(anyList());

        //when
        List<Item> actual = exercise5.convert(transaction);

        //then
        assertEquals(expectedItemCount, actual.size());
    }

    @Test
    @DisplayName("Should return list of items when filter sale returns false")
    void convertReturnsListWhenFilterSaleFalse() {
        //given
        List<Item> items = List.of(
                new Item(new Sale(), "Fuel1", new Return(), new Data("typeA")),
                new Item(null, null, new Return(), new Data(SOME_TYPE)),
                new Item(null, null, new Return(), new Data("typeB")),
                new Item(new Sale(), null, null, new Data("typeC")),
                new Item(new Sale(), "Fuel4", new Return(), null),
                new Item(null, "Fuel4", new Return(), null),
                new Item(null, null, null, null)
        );
        Transaction transaction = new Transaction(items);
        int expectedItemCount = 4;
        Item dummyItem = new Item(new Sale(), "dummy", new Return(), new Data("dummy"));

        doReturn(items).when(exercise5).filterItems(anyList());
        doReturn(false).when(exercise5).filterSale(any(Sale.class));
        doReturn(true).when(exercise5).isReturnTransaction(transaction);
        doReturn(dummyItem).when(exercise5).createItem(any(Item.class));
        //when
        List<Item> actual = exercise5.convert(transaction);

        //then
        assertEquals(expectedItemCount, actual.size());
    }

    @Test
    @DisplayName("Should return list of items when is return transaction returns false")
    void convertReturnsListWhenIsReturnTransactionFalse() {
        //given
        List<Item> items = List.of(
                new Item(new Sale(), "Fuel1", new Return(), new Data("typeA")),
                new Item(null, null, new Return(), new Data(SOME_TYPE)),
                new Item(null, null, new Return(), new Data("typeB")),
                new Item(new Sale(), null, null, new Data("typeC")),
                new Item(new Sale(), "Fuel4", new Return(), null),
                new Item(null, "Fuel4", new Return(), null),
                new Item(null, null, null, null)
        );
        Transaction transaction = new Transaction(items);
        int expectedItemCount = 4;
        Item dummyItem = new Item(new Sale(), "dummy", new Return(), new Data("dummy"));

        doReturn(items).when(exercise5).filterItems(anyList());
        doReturn(true).when(exercise5).filterSale(any(Sale.class));
        doReturn(false).when(exercise5).isReturnTransaction(transaction);
        doReturn(dummyItem).when(exercise5).createItem(any(Item.class));

        //when
        List<Item> actual = exercise5.convert(transaction);

        //then
        assertEquals(expectedItemCount, actual.size());
    }

    @Test
    @DisplayName("Should return empty list when transaction is null")
    void convertReturnsEmptyListWhenTransactionIsNull() {
        //when
        assertEquals(List.of(), exercise5.convert(null));
    }

    @Test
    @DisplayName("Should return empty list when items is null")
    void convertReturnsEmptyListWhenItemsIsNull() {
        //given
        doReturn(List.of()).when(exercise5).filterItems(null);

        //when
        //then
        assertEquals(List.of(), exercise5.convert(new Transaction(null)));
    }

    //createItem return item\null

    @Test
    @DisplayName("Should return empty list when create item returns null")
    void convertReturnsEmptyListWhenCreateItemNull() {
        //given
        Item item = new Item(new Sale(), "Fuel1", new Return(), new Data("typeA"));
        List<Item> items = List.of(item);
        Transaction transaction = new Transaction(items);
        List<Item> expected = List.of();

        doReturn(items).when(exercise5).filterItems(items);
        doReturn(true).when(exercise5).filterSale(item.sale());
        doReturn(null).when(exercise5).createItem(item);

        //when
        List<Item> actual = exercise5.convert(transaction);

        //then
        assertEquals(expected, actual);

        verify(exercise5).filterItems(items);
        verify(exercise5).filterSale(item.sale());
        verify(exercise5).createItem(item);
    }
}