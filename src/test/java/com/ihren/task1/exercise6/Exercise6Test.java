package com.ihren.task1.exercise6;

import com.ihren.task1.exercise6.models.Item;
import com.ihren.task1.exercise6.Exercise6;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Exercise6Test {
    private final Exercise6 exercise6 = new Exercise6();

    @ParameterizedTest
    @MethodSource("provideItemsAndExpectedResult")
    @DisplayName("Should return expected boolean for given item")
    void filterReturnsExpectedBoolean(Item item, boolean expected) {
        assertEquals(expected, exercise6.filter(item));
    }

    private static Stream<Arguments> provideItemsAndExpectedResult() {
        return Stream.of(
            Arguments.of(new Item("SOME_TYPE", null), true),
                Arguments.of(new Item(null, Boolean.TRUE), true),
                Arguments.of(new Item("NOT_SOME_TYPE", null), false),
                Arguments.of(new Item("NOT_SOME_TYPE", Boolean.TRUE), true),
                Arguments.of(new Item(null, null), false),
                Arguments.of(new Item(null, Boolean.FALSE), false),
                Arguments.of(null, false)
        );
    }
}