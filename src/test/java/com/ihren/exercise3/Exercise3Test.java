package com.ihren.exercise3;

import com.ihren.exercise3.models.Element;
import com.ihren.exercise3.models.Item;
import com.ihren.exercise3.models.SomeWrongType;
import com.ihren.exercise3.models.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Exercise3Test {

    private final Exercise3 exercise3 = new Exercise3();

    @Test
    void filterShouldReturnListOfItemsTest() {
        //given
        List<Item> expected = List.of(
                new Item(
                        "typeB",
                        new Element("2"),
                        "b"
                ),
                new Item(
                        "typeC",
                        new Element("3"),
                        "c"
                ),
                new Item(
                        "typeD",
                        new Element("4"),
                        "d"
                )
        );

        //when
        try(MockedStatic<SomeWrongType> mockedStatic = mockStatic(SomeWrongType.class)) {
            mockedStatic.when(() -> SomeWrongType.contentEquals(anyString())).thenReturn(false);
            mockedStatic.when(() -> SomeWrongType.contentEquals("someWrongContent")).thenReturn(true);

            List<Item> actual = exercise3.filter(Transaction.ITEMS);

            //then
            assertEquals(expected, actual);
        }
    }

    @Test
    void filterShouldThrowNumberFormatExceptionWhenElementIdIsInvalidFormatTest() {
        //given
        List<Item> items = List.of(
                new Item(
                        "a",
                        new Element("notANumber"),
                        "a"
                )
        );

        //when
        try(MockedStatic<SomeWrongType> mockedStatic = mockStatic(SomeWrongType.class)) {
            mockedStatic.when(() -> SomeWrongType.contentEquals(anyString())).thenReturn(false);

            //then
            assertThrows(NumberFormatException.class, () -> exercise3.filter(Transaction.ITEMS));
        }
    }

    @Test
    void filterShouldThrowNullPointerExceptionWhenNoTransactionIdFound() {
        //given
        List<Item> nonexistentTransactionId = List.of(
                    new Item("typeA",
                            new Element("1"),
                            "nonexistentId"
                    )
        );

        //when
        try(MockedStatic<SomeWrongType> mockedStatic = mockStatic(SomeWrongType.class)) {
            mockedStatic.when(() -> SomeWrongType.contentEquals(anyString())).thenReturn(false);

            //then
            assertThrows(NullPointerException.class, () -> exercise3.filter(nonexistentTransactionId));
        }
    }
}