package com.ihren.exercise7;

import com.ihren.exercise7.models.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise7Test {

    Exercise7 exercise7 = new Exercise7();

    @Test
    void filterShouldReturnListOfFilteredItemsTest() {
        //given
        List<Item> expected = List.of(
                new Item(5L, 3L, null, Action.ACTIVE, null),
                new Item(6L, 4L, null, Action.CANCELED, null),
                new Item(null, null, null, null, null)
        );

        //when
        List<Item> actual = exercise7.filter(ModelUtils.ITEMS);

        //then
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void filterShouldReturnEmptyListIfItemsIsNullTest() {
        //given
        //when
        List<Item> actual = exercise7.filter(null);

        //then
        assertTrue(actual.isEmpty());
    }
}