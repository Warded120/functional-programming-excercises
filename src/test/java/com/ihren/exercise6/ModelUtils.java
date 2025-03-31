package com.ihren.exercise6;

import com.ihren.exercise6.models.Item;

import java.util.Arrays;
import java.util.List;

public class ModelUtils {

    public static final List<Item> ITEMS = List.of(
            new Item(
                    "SOME_TYPE",
                    null
            ),
            new Item(
                    null,
                    Boolean.TRUE
            ),
            new Item(
                    "NOT_SOME_TYPE",
                    null
            ),
            new Item(
                    null,
                    null
            ),
            new Item(
                    null,
                    Boolean.FALSE
            )
    );
}
