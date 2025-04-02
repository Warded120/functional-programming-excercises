package com.ihren.exercise3;

import com.ihren.exercise3.models.Element;
import com.ihren.exercise3.models.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class Exercise3Test {

    @Spy
    @InjectMocks
    private Exercise3 exercise3;

    private static final Integer ID_UPPER_BOUND = 7;
    private static final Integer ID_LOWER_BOUND = 2;
    private static final String SOME_WRONG_TYPE = "SOME_WRONG_TYPE";

    @Test
    @DisplayName("Should return empty list when items is null")
    void filterReturnsEmptyListWhenItemsIsNull() {
        //when
        assertEquals(List.of(), exercise3.filter(null));
    }

    @Test
    @DisplayName("Should return empty list when items is empty")
    void filterReturnsEmptyListWhenItemsIsEmpty() {
        //when
        assertEquals(List.of(), exercise3.filter(List.of()));
    }

    @Test
    @DisplayName("Should return list of items")
    void filterReturnsListOfItems() {
        //given
        List<Item> items =
                List.of(
                    new Item("typeA", new Element("1"), "a"),
                    new Item("typeB", new Element("2"), "b"),
                    new Item("typeC", new Element("3"), "c"),
                    new Item("typeD", new Element("4"), "d"),
                    new Item("someWrongContent", new Element("notANumber"), "e")
                );
        List<Item> expected = 
                List.of(
                    new Item("typeB", new Element("2"), "b"),
                    new Item("typeC", new Element("3"), "c"),
                    new Item("typeD", new Element("4"), "d")
        );  
        doReturn("1").when(exercise3).getElementId("a");
        doReturn("2").when(exercise3).getElementId("b");
        doReturn("3").when(exercise3).getElementId("c");
        doReturn("4").when(exercise3).getElementId("d");
        doReturn("NotANumber").when(exercise3).getElementId("e");

        //when
        //then
        List<Item> actual = exercise3.filter(items);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return empty list when getElementId returns null")
    void filterReturnsEmptyListWhenGetElementIdReturnsNull() {
        //given
        List<Item> items =
                List.of(
                        new Item("typeA", new Element("1"), "a")
                );
        List<Item> expected =
                List.of();

        doReturn(null).when(exercise3).getElementId("a");

        //when
        List<Item> actual = exercise3.filter(items);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should throw NullPointerException when transaction ID is not found")
    void filterThrowsNullPointerExceptionWhenTransactionIdNotFound() {
        //given
        List<Item> nonexistentTransactionId = List.of(
                    new Item("typeA",
                            new Element("1"),
                            "nonexistentId"
                    )
        );

        doThrow(NullPointerException.class).when(exercise3).getElementId("nonexistentId");

        //when
        //then
        assertThrows(NullPointerException.class, () -> exercise3.filter(nonexistentTransactionId));
    }
}