package com.ihren.exercise7;

import com.ihren.exercise7.models.Action;
import com.ihren.exercise7.models.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Exercise7Test {

    private final Exercise7 exercise7 = new Exercise7();

    @Test
    @DisplayName("Should return list of filtered items")
    void filterReturnsFilteredItems() {
        //given
        Item item1 = mock(Item.class);
        when(item1.id()).thenReturn(1L);
        when(item1.parentId()).thenReturn(null);
        when(item1.isCancelled()).thenReturn(Boolean.TRUE);
        when(item1.action()).thenReturn(Action.ACTIVE);
        when(item1.returnReason()).thenReturn(null);

        Item item2 = mock(Item.class);
        when(item2.id()).thenReturn(2L);
        when(item2.parentId()).thenReturn(7L);
        when(item2.isCancelled()).thenReturn(Boolean.TRUE);
        when(item2.action()).thenReturn(Action.CANCELED);
        when(item2.returnReason()).thenReturn(null);

        Item item3 = mock(Item.class);
        when(item3.id()).thenReturn(3L);
        when(item3.parentId()).thenReturn(2L);
        when(item3.isCancelled()).thenReturn(Boolean.FALSE);
        when(item3.action()).thenReturn(null);
        when(item3.returnReason()).thenReturn("Customer changed mind");

        Item item4 = mock(Item.class);
        when(item4.id()).thenReturn(4L);
        when(item4.parentId()).thenReturn(null);
        when(item4.isCancelled()).thenReturn(Boolean.FALSE);
        when(item4.action()).thenReturn(Action.SKIPPED);
        when(item4.returnReason()).thenReturn(null);

        Item item5 = mock(Item.class);
        when(item5.id()).thenReturn(5L);
        when(item5.parentId()).thenReturn(3L);
        when(item5.isCancelled()).thenReturn(null);
        when(item5.action()).thenReturn(Action.ACTIVE);
        when(item5.returnReason()).thenReturn(null);

        Item item6 = mock(Item.class);
        when(item6.id()).thenReturn(6L);
        when(item6.parentId()).thenReturn(4L);
        when(item6.isCancelled()).thenReturn(null);
        when(item6.action()).thenReturn(Action.CANCELED);
        when(item6.returnReason()).thenReturn(null);

        Item item7 = mock(Item.class);
        when(item7.id()).thenReturn(null);
        when(item7.parentId()).thenReturn(null);
        when(item7.isCancelled()).thenReturn(null);
        when(item7.action()).thenReturn(null);
        when(item7.returnReason()).thenReturn(null);

        Item item8 = mock(Item.class);
        when(item8.id()).thenReturn(7L);
        when(item8.parentId()).thenReturn(null);
        when(item8.isCancelled()).thenReturn(null);
        when(item8.action()).thenReturn(null);
        when(item8.returnReason()).thenReturn(null);

        List<Item> items = List.of(item1, item2, item3, item4, item5, item6, item7, item8);
        List<Item> expected = List.of(item5, item6, item7);

        //when
        List<Item> actual = exercise7.filter(items);

        //then
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return empty list if items is null")
    void filterReturnsEmptyListIfItemsNull() {
        //given
        //when
        List<Item> actual = exercise7.filter(null);

        //then
        assertTrue(actual.isEmpty());
    }
}