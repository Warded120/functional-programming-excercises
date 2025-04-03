package com.ihren.task1.exercise5;

import com.ihren.task1.exercise5.Exercise5;
import com.ihren.task1.exercise5.models.Data;
import com.ihren.task1.exercise5.models.Item;
import com.ihren.task1.exercise5.models.Return;
import com.ihren.task1.exercise5.models.Sale;
import com.ihren.task1.exercise5.models.Transaction;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Exercise5Test {
    private static final String SOME_TYPE = "SOME_TYPE";

    @Spy
    @InjectMocks
    private Exercise5 exercise5;

    @Test
    @DisplayName("Should return list of items when all filter functions return true")
    void convertReturnsListWhenAllFiltersTrue() {
        //given
        Sale sale = mock(Sale.class);
        Return returnMock = mock(Return.class);

        Data data2 = mock(Data.class);
        when(data2.type()).thenReturn(SOME_TYPE);

        Data data3 = mock(Data.class);
        when(data3.type()).thenReturn(null);

        Data data4 = mock(Data.class);
        when(data4.type()).thenReturn("typeB");

        Item item1 = mock(Item.class);
        when(item1.sale()).thenReturn(sale);

        Item item2 = mock(Item.class);
        when(item2.sale()).thenReturn(null);
        when(item2.aReturn()).thenReturn(returnMock);
        when(item2.data()).thenReturn(data2);

        Item item3 = mock(Item.class);
        when(item3.sale()).thenReturn(null);
        when(item3.fuelSale()).thenReturn(null);
        when(item3.aReturn()).thenReturn(returnMock);
        when(item3.data()).thenReturn(data3);

        Item item4 = mock(Item.class);
        when(item4.sale()).thenReturn(null);
        when(item4.fuelSale()).thenReturn(null);
        when(item4.aReturn()).thenReturn(returnMock);
        when(item4.data()).thenReturn(data4);

        Item item5 = mock(Item.class);
        when(item5.sale()).thenReturn(sale);

        Item item6 = mock(Item.class);
        when(item6.sale()).thenReturn(sale);

        Item item7 = mock(Item.class);
        when(item7.sale()).thenReturn(null);
        when(item7.fuelSale()).thenReturn("Fuel3");
        when(item7.aReturn()).thenReturn(returnMock);
        when(item7.data()).thenReturn(null);

        Item item8 = mock(Item.class);
        when(item8.sale()).thenReturn(null);
        when(item8.fuelSale()).thenReturn(null);
        when(item8.aReturn()).thenReturn(null);

        List<Item> items = List.of(item1, item2, item3, item4, item5, item6, item7, item8);

        Transaction transaction = mock(Transaction.class);
        when(transaction.items()).thenReturn(items);

        List<Item> expected = List.of(item1, item2, item5, item6, item7);

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
        Item item1 = mock(Item.class);
        List<Item> items = List.of(item1, item1, item1, item1, item1, item1, item1);

        Transaction transaction = mock(Transaction.class);
        when(transaction.items()).thenReturn(items);
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
        Sale sale = mock(Sale.class);
        Return aReturn = mock(Return.class);

        Data data1 = mock(Data.class);
        when(data1.type()).thenReturn("typeA");

        Data data2 = mock(Data.class);
        when(data2.type()).thenReturn(SOME_TYPE);

        Data data3 = mock(Data.class);
        when(data3.type()).thenReturn("typeB");

        Item item1 = mock(Item.class);
        when(item1.sale()).thenReturn(sale);
        when(item1.fuelSale()).thenReturn("Fuel1");
        when(item1.aReturn()).thenReturn(aReturn);
        when(item1.data()).thenReturn(data1);

        Item item2 = mock(Item.class);
        when(item2.sale()).thenReturn(null);
        when(item2.aReturn()).thenReturn(aReturn);
        when(item2.data()).thenReturn(data2);

        Item item3 = mock(Item.class);
        when(item3.sale()).thenReturn(null);
        when(item3.fuelSale()).thenReturn(null);
        when(item3.aReturn()).thenReturn(aReturn);
        when(item3.data()).thenReturn(data3);

        Item item4 = mock(Item.class);
        when(item4.sale()).thenReturn(sale);
        when(item4.fuelSale()).thenReturn(null);
        when(item4.aReturn()).thenReturn(null);

        Item item5 = mock(Item.class);
        when(item5.sale()).thenReturn(sale);
        when(item5.fuelSale()).thenReturn("Fuel4");
        when(item5.aReturn()).thenReturn(aReturn);
        when(item5.data()).thenReturn(null);

        Item item6 = mock(Item.class);
        when(item6.sale()).thenReturn(null);
        when(item6.fuelSale()).thenReturn("Fuel4");
        when(item6.aReturn()).thenReturn(aReturn);
        when(item6.data()).thenReturn(null);

        Item item7 = mock(Item.class);
        when(item7.sale()).thenReturn(null);
        when(item7.fuelSale()).thenReturn(null);
        when(item7.aReturn()).thenReturn(null);
        List<Item> items = List.of(item1, item2, item3, item4, item5, item6, item7);

        Transaction transaction = mock(Transaction.class);
        when(transaction.items()).thenReturn(items);

        int expectedItemCount = 4;
        Item dummyItem = mock(Item.class);

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
        Sale sale = mock(Sale.class);
        Return returnMock = mock(Return.class);

        Item item1 = mock(Item.class);
        when(item1.sale()).thenReturn(sale);

        Item item2 = mock(Item.class);
        when(item2.sale()).thenReturn(null);
        when(item2.fuelSale()).thenReturn(null);
        when(item2.aReturn()).thenReturn(returnMock);

        Item item3 = mock(Item.class);
        when(item3.sale()).thenReturn(null);
        when(item3.fuelSale()).thenReturn(null);
        when(item3.aReturn()).thenReturn(returnMock);

        Item item4 = mock(Item.class);
        when(item4.sale()).thenReturn(sale);

        Item item5 = mock(Item.class);
        when(item5.sale()).thenReturn(sale);

        Item item6 = mock(Item.class);
        when(item6.sale()).thenReturn(null);
        when(item6.fuelSale()).thenReturn("Fuel4");
        when(item6.aReturn()).thenReturn(returnMock);

        Item item7 = mock(Item.class);
        when(item7.sale()).thenReturn(null);
        when(item7.fuelSale()).thenReturn(null);
        when(item7.aReturn()).thenReturn(null);

        List<Item> items = List.of(item1, item2, item3, item4, item5, item6, item7);

        Transaction transaction = mock(Transaction.class);
        when(transaction.items()).thenReturn(items);

        int expectedItemCount = 4;
        Item dummyItem = mock(Item.class);

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
        Transaction transaction = mock(Transaction.class);
        when(transaction.items()).thenReturn(null);

        doReturn(List.of()).when(exercise5).filterItems(null);

        //when
        //then
        assertEquals(List.of(), exercise5.convert(transaction));
    }

    //createItem return item\null

    @Test
    @DisplayName("Should return empty list when create item returns null")
    void convertReturnsEmptyListWhenCreateItemNull() {
        //given
        Sale sale = mock(Sale.class);
        Item item = mock(Item.class);
        when(item.sale()).thenReturn(sale);

        List<Item> items = List.of(item);

        Transaction transaction = mock(Transaction.class);
        when(transaction.items()).thenReturn(items);

        List<Item> expected = List.of();

        doReturn(items).when(exercise5).filterItems(items);
        doReturn(true).when(exercise5).filterSale(sale);
        doReturn(null).when(exercise5).createItem(item);

        //when
        List<Item> actual = exercise5.convert(transaction);

        //then
        assertEquals(expected, actual);

        verify(exercise5).filterItems(items);
        verify(exercise5).filterSale(sale);
        verify(exercise5).createItem(item);
    }
}