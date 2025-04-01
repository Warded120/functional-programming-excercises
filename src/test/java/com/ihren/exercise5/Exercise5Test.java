package com.ihren.exercise5;

import com.ihren.exercise5.models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Exercise5Test {

    private final Exercise5 exercise5 = new Exercise5();
    private static final Item dummyItem = new Item(new Sale(), "dummy", new Return(), new Data("dummy"));

    MockedStatic<Exercise5> mockedStatic;

    @BeforeEach
    void setUp() {
        mockedStatic = mockStatic(Exercise5.class);
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close();
    }

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

        mockedStatic.when(() -> Exercise5.filterItems(anyList()))
                .thenReturn(ModelUtils.ITEMS);
        mockedStatic.when(() -> Exercise5.filterSale(any(Sale.class)))
                .thenReturn(true);
        mockedStatic.when(() -> Exercise5.isReturnTransaction(any(Transaction.class)))
                .thenReturn(true);
        mockedStatic.when(() -> Exercise5.createItem(any(Item.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.<Item>getArgument(0));

        //when
        List<Item> actual = exercise5.convert(ModelUtils.TRANSACTION);

        //then
        assertEquals(expected, actual);

        mockedStatic.verify(() -> Exercise5.filterItems(anyList()), times(1));
        mockedStatic.verify(() -> Exercise5.filterSale(any(Sale.class)), times(3));
        mockedStatic.verify(() -> Exercise5.isReturnTransaction(any(Transaction.class)), times(3));
        mockedStatic.verify(() -> Exercise5.createItem(any(Item.class)), times(5));
    }

    @Test
    void convertShouldReturnListOfItemsWhenFilterItemsReturnFalseTest() {
        //given
        int expectedItemCount = 0;

        mockedStatic.when(() -> Exercise5.filterItems(anyList())).thenReturn(List.of());
        mockedStatic.when(() -> Exercise5.filterSale(any(Sale.class))).thenReturn(true);
        mockedStatic.when(() -> Exercise5.isReturnTransaction(any(Transaction.class))).thenReturn(true);

        mockedStatic.when(() -> Exercise5.createItem(any(Item.class))).thenReturn(dummyItem);

        //when
        List<Item> actual = exercise5.convert(ModelUtils.TRANSACTION);

        //then
        assertEquals(expectedItemCount, actual.size());
    }

    @Test
    void convertShouldReturnListOfItemsWhenFilterSaleReturnFalseTest() {
        //given
        int expectedItemCount = 4;

        mockedStatic.when(() -> Exercise5.filterItems(anyList())).thenReturn(ModelUtils.ITEMS);
        mockedStatic.when(() -> Exercise5.filterSale(any(Sale.class))).thenReturn(false);
        mockedStatic.when(() -> Exercise5.isReturnTransaction(any(Transaction.class))).thenReturn(true);
        mockedStatic.when(() -> Exercise5.createItem(any(Item.class))).thenReturn(dummyItem);

        //when
        List<Item> actual = exercise5.convert(ModelUtils.TRANSACTION);

        //then
        assertEquals(expectedItemCount, actual.size());
    }

    @Test
    void convertShouldReturnListOfItemsWhenIsReturnTransactionReturnFalseTest() {
        //given
        int expectedItemCount = 4;

        mockedStatic.when(() -> Exercise5.filterItems(anyList())).thenReturn(ModelUtils.ITEMS);
        mockedStatic.when(() -> Exercise5.filterSale(any(Sale.class))).thenReturn(true);
        mockedStatic.when(() -> Exercise5.isReturnTransaction(any(Transaction.class))).thenReturn(false);
        mockedStatic.when(() -> Exercise5.createItem(any(Item.class))).thenReturn(dummyItem);

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