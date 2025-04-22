package com.ihren.task1.exercise3;

import com.ihren.task1.exercise3.models.Item;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        Item item1 = mock(Item.class);
        when(item1.type()).thenReturn("typeA");
        when(item1.transactionId()).thenReturn("a");

        Item item2 = mock(Item.class);
        when(item2.type()).thenReturn("typeB");
        when(item2.transactionId()).thenReturn("b");

        Item item3 = mock(Item.class);
        when(item3.type()).thenReturn("typeC");
        when(item3.transactionId()).thenReturn("c");

        Item item4 = mock(Item.class);
        when(item4.type()).thenReturn("typeD");
        when(item4.transactionId()).thenReturn("d");

        Item item5 = mock(Item.class);
        when(item5.type()).thenReturn("typeE");
        when(item5.transactionId()).thenReturn("e");

        Item item6 = mock(Item.class);
        when(item6.type()).thenReturn(SOME_WRONG_TYPE);

        List<Item> items = List.of(item1, item2, item3, item4, item5, item6);
        List<Item> expected = List.of(item2, item3, item4, item5);

        doReturn("1").when(exercise3).getElementId("a");
        doReturn(ID_LOWER_BOUND.toString()).when(exercise3).getElementId("b");
        doReturn("3").when(exercise3).getElementId("c");
        doReturn("4").when(exercise3).getElementId("d");
        doReturn(ID_UPPER_BOUND.toString()).when(exercise3).getElementId("e");

        //when
        //then
        List<Item> actual = exercise3.filter(items);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return empty list when getElementId returns null")
    void filterReturnsEmptyListWhenGetElementIdReturnsNull() {
        //given
        Item item = mock(Item.class);
        when(item.type()).thenReturn("typeA");
        when(item.transactionId()).thenReturn("a");

        List<Item> items = List.of(item);
        List<Item> expected = List.of();

        doReturn(null).when(exercise3).getElementId("a");

        //when
        List<Item> actual = exercise3.filter(items);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should throw NullPointerException when transaction ID is not found")
    void filterThrowsNullPointerExceptionWhenTransactionIdNotFound() {
        //given
        Item item = mock(Item.class);
        when(item.type()).thenReturn("typeA");
        when(item.transactionId()).thenReturn("nonexistentId");

        List<Item> nonexistentTransactionId = List.of(item);

        doThrow(NullPointerException.class).when(exercise3).getElementId("nonexistentId");

        //when
        //then
        assertThrows(NullPointerException.class, () -> exercise3.filter(nonexistentTransactionId));
    }
}