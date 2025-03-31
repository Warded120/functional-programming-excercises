package com.ihren.exercise7;

import com.ihren.exercise7.models.*;

import java.util.List;

public class ModelUtils {
    public static final List<Item> ITEMS = List.of(
            new Item(1L, null, true, Action.ACTIVE, null),
            new Item(2L, 2L, true, Action.CANCELED, null),
            new Item(3L, 2L, false, null, "Customer changed mind"),
            new Item(4L, null, false, Action.SKIPPED, null),
            new Item(5L, 3L, null, Action.ACTIVE, null),
            new Item(6L, 4L, null, Action.CANCELED, null),
            new Item(null, null, null, null, null)
    );
}
