package com.ihren.exercise5;

import com.ihren.exercise5.models.*;

import java.util.List;

public class ModelUtils {
    public static final List<Item> ITEMS = List.of(
            new Item(new Sale(), "Fuel1", new Return(), new Data("typeA")),
            new Item(null, null, new Return(), new Data("SOME_TYPE")),
            new Item(null, null, new Return(), new Data("typeB")),
            new Item(new Sale(), null, null, new Data("typeC")),
            new Item(new Sale(), "Fuel4", new Return(), null),
            new Item(null, "Fuel4", new Return(), null),
            new Item(null, null, null, null)
    );
    public static final Transaction TRANSACTION = new Transaction(ITEMS);

}
