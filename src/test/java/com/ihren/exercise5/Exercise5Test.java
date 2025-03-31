package com.ihren.exercise5;

import com.ihren.exercise5.models.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Exercise5Test {

    private final Exercise5 exercise5 = new Exercise5();

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

        try(MockedStatic<Exercise5> mockedStatic = mockStatic(Exercise5.class)) {
            mockedStatic.when(() -> Exercise5.filterItem(any(Item.class))).thenReturn(true);
            mockedStatic.when(() -> Exercise5.filterSale(any(Sale.class))).thenReturn(true);
            mockedStatic.when(() -> Exercise5.isReturnTransaction(any(Transaction.class))).thenReturn(true);
            mockedStatic.when(() -> Exercise5.createItem(any(Item.class)))
                    .thenAnswer(invocationOnMock -> invocationOnMock.<Item>getArgument(0));

            //when
            List<Item> actual = exercise5.convert(ModelUtils.transaction);

            //then
            assertEquals(expected, actual);

            mockedStatic.verify(() -> Exercise5.filterItem(any(Item.class)), times(7));
            mockedStatic.verify(() -> Exercise5.filterSale(any(Sale.class)), times(3));
            mockedStatic.verify(() -> Exercise5.isReturnTransaction(any(Transaction.class)), times(3));
            mockedStatic.verify(() -> Exercise5.createItem(any(Item.class)), times(5));
        }
    }

    @Test
    void convertShouldReturnListOfItemsWhenFilterItemReturnFalseTest() {
        //given
        int expectedItemCount = 0;

        try(MockedStatic<Exercise5> mockedStatic = mockStatic(Exercise5.class)) {
            mockedStatic.when(() -> Exercise5.filterItem(any(Item.class))).thenReturn(false);
            mockedStatic.when(() -> Exercise5.filterSale(any(Sale.class))).thenReturn(true);
            mockedStatic.when(() -> Exercise5.isReturnTransaction(any(Transaction.class))).thenReturn(true);
            mockedStatic.when(() -> Exercise5.createItem(any(Item.class))).thenReturn(new Item());

            //when
            List<Item> actual = exercise5.convert(ModelUtils.transaction);

            //then
            assertEquals(expectedItemCount, actual.size());
        }
    }

    @Test
    void convertShouldReturnListOfItemsWhenFilterSaleReturnFalseTest() {
        //given
        int expectedItemCount = 4;

        try(MockedStatic<Exercise5> mockedStatic = mockStatic(Exercise5.class)) {
            mockedStatic.when(() -> Exercise5.filterItem(any(Item.class))).thenReturn(true);
            mockedStatic.when(() -> Exercise5.filterSale(any(Sale.class))).thenReturn(false);
            mockedStatic.when(() -> Exercise5.isReturnTransaction(any(Transaction.class))).thenReturn(true);
            mockedStatic.when(() -> Exercise5.createItem(any(Item.class))).thenReturn(new Item());

            //when
            List<Item> actual = exercise5.convert(ModelUtils.transaction);

            //then
            assertEquals(expectedItemCount, actual.size());
        }
    }

    @Test
    void convertShouldReturnListOfItemsWhenIsReturnTransactionReturnFalseTest() {
        //given
        int expectedItemCount = 4;

        try(MockedStatic<Exercise5> mockedStatic = mockStatic(Exercise5.class)) {
            mockedStatic.when(() -> Exercise5.filterItem(any(Item.class))).thenReturn(true);
            mockedStatic.when(() -> Exercise5.filterSale(any(Sale.class))).thenReturn(true);
            mockedStatic.when(() -> Exercise5.isReturnTransaction(any(Transaction.class))).thenReturn(false);
            mockedStatic.when(() -> Exercise5.createItem(any(Item.class))).thenReturn(new Item());

            //when
            List<Item> actual = exercise5.convert(ModelUtils.transaction);

            //then
            assertEquals(expectedItemCount, actual.size());
        }
    }
}