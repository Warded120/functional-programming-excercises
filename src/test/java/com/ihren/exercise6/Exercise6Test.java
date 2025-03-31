package com.ihren.exercise6;

import com.ihren.exercise6.models.Item;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Exercise6Test {

    private final Exercise6 exercise6 = new Exercise6();

    static Stream<Arguments> provideItemsAndExpectedResult() {
        return Stream.of(
                Arguments.of(new Item("SOME_TYPE", null), true),
                Arguments.of(new Item(null, Boolean.TRUE), true),
                Arguments.of(new Item("NOT_SOME_TYPE", null), false),
                Arguments.of(new Item(null, null), false),
                Arguments.of(new Item(null, Boolean.FALSE), false),
                Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideItemsAndExpectedResult")
    void filterShouldReturnExpectedBooleanTest(Item item, boolean expected) {
        assertEquals(expected, exercise6.filter(item));
    }
}