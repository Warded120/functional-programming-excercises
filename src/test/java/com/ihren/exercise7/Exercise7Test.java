package com.ihren.exercise7;

import com.ihren.exercise7.models.Action;
import com.ihren.exercise7.models.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Exercise7Test {

    private final Exercise7 exercise7 = new Exercise7();

    @Test
    @DisplayName("Should return list of filtered items")
    void filterReturnsFilteredItems() {
        //given
        List<Item> items = List.of(
                new Item(1L, null, Boolean.TRUE, Action.ACTIVE, null),
                new Item(2L, 7L, Boolean.TRUE, Action.CANCELED, null),
                new Item(3L, 2L, Boolean.FALSE, null, "Customer changed mind"),
                new Item(4L, null, Boolean.FALSE, Action.SKIPPED, null),
                new Item(5L, 3L, null, Action.ACTIVE, null),
                new Item(6L, 4L, null, Action.CANCELED, null),
                new Item(null, null, null, null, null),
                new Item(7L, null, null, null, null)
        );
        List<Item> expected = List.of(
                new Item(5L, 3L, null, Action.ACTIVE, null),
                new Item(6L, 4L, null, Action.CANCELED, null),
                new Item(null, null, null, null, null)
        );

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