package com.ihren.exercise3;

import com.ihren.exercise3.models.Element;
import com.ihren.exercise3.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

class Exercise3Test {

    @Spy
    @InjectMocks
    private Exercise3 exercise3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
        doReturn("1").when(exercise3).getElementId("a");
        doReturn("2").when(exercise3).getElementId("b");
        doReturn("3").when(exercise3).getElementId("c");
        doReturn("4").when(exercise3).getElementId("d");
        doReturn("NotANumber").when(exercise3).getElementId("e");

        //when
        //then
        List<Item> actual = assertDoesNotThrow(() -> exercise3.filter(ModelUtils.ITEMS));
        assertEquals(expected, actual);
    }

    @Test
    void filterShouldThrowNullPointerExceptionWhenTransactionIdNotFound() {
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

    @Test
    void filterShouldReturnEmptyListWhenItemsIsNull() {
        assertEquals(List.of(), exercise3.filter(null));
    }
}