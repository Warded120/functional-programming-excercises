package com.ihren.exercise5;

import com.ihren.exercise5.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Exercise5Test {

    @Spy
    @InjectMocks
    private Exercise5 exercise5;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static final Item dummyItem = new Item(new Sale(), "dummy", new Return(), new Data("dummy"));

    @Test
    void convertShouldReturnListOfItemsWhenAllFilterFunctionsReturnTrueTest() {
        //given
        List<Item> expected = List.of(
                new Item(new Sale(), "Fuel1", new Return(), new Data("typeA")),
                new Item(null, null, new Return(), new Data("SOME_TYPE")),
                new Item(new Sale(), null, null, new Data("typeC")),
                new Item(new Sale(), "Fuel4", new Return(), null),
                new Item(null, "Fuel4", new Return(), null)
        );

        doReturn(ModelUtils.ITEMS).when(exercise5).filterItems(anyList());
        doReturn(true).when(exercise5).filterSale(any(Sale.class));
        doReturn(true).when(exercise5).isReturnTransaction(any(Transaction.class));
        doAnswer(invocationOnMock ->
                invocationOnMock.getArgument(0)
        )
        .when(exercise5).createItem(any(Item.class));

        //when
        List<Item> actual = exercise5.convert(ModelUtils.TRANSACTION);

        //then
        assertEquals(expected, actual);

        verify(exercise5, times(1)).filterItems(anyList());
        verify(exercise5, times(3)).filterSale(any(Sale.class));
        verify(exercise5, times(3)).isReturnTransaction(any(Transaction.class));
        verify(exercise5, times(5)).createItem(any(Item.class));
    }

    @Test
    void convertShouldReturnListOfItemsWhenFilterItemsReturnEmptyListTest() {
        //given
        int expectedItemCount = 0;

        doReturn(List.of()).when(exercise5).filterItems(anyList());
        doReturn(true).when(exercise5).filterSale(any(Sale.class));
        doReturn(true).when(exercise5).isReturnTransaction(any(Transaction.class));
        doReturn(dummyItem).when(exercise5).createItem(any(Item.class));


        //when
        List<Item> actual = exercise5.convert(ModelUtils.TRANSACTION);

        //then
        assertEquals(expectedItemCount, actual.size());
    }

    @Test
    void convertShouldReturnListOfItemsWhenFilterSaleReturnFalseTest() {
        //given
        int expectedItemCount = 4;

        doReturn(ModelUtils.ITEMS).when(exercise5).filterItems(anyList());
        doReturn(false).when(exercise5).filterSale(any(Sale.class));
        doReturn(true).when(exercise5).isReturnTransaction(any(Transaction.class));
        doReturn(dummyItem).when(exercise5).createItem(any(Item.class));
        //when
        List<Item> actual = exercise5.convert(ModelUtils.TRANSACTION);

        //then
        assertEquals(expectedItemCount, actual.size());
    }

    @Test
    void convertShouldReturnListOfItemsWhenIsReturnTransactionReturnFalseTest() {
        //given
        int expectedItemCount = 4;

        doReturn(ModelUtils.ITEMS).when(exercise5).filterItems(anyList());
        doReturn(true).when(exercise5).filterSale(any(Sale.class));
        doReturn(false).when(exercise5).isReturnTransaction(any(Transaction.class));
        doReturn(dummyItem).when(exercise5).createItem(any(Item.class));

        //when
        List<Item> actual = exercise5.convert(ModelUtils.TRANSACTION);

        //then
        assertEquals(expectedItemCount, actual.size());
    }

    @Test
    void convertShouldReturnEmptyListWhenTransactionIsNullTest() {
        assertEquals(List.of(), exercise5.convert(null));
    }
}