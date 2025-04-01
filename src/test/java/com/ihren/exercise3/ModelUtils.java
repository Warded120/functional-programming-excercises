package com.ihren.exercise3;

import com.ihren.exercise3.models.Element;
import com.ihren.exercise3.models.Item;

import java.util.List;

public class ModelUtils {
    public static final List<Item> ITEMS =
            List.of(
                new Item("typeA",
                        new Element("1"),
                        "a"
                ),
                new Item("typeB",
                        new Element("2"),
                        "b"
                ),
                new Item("typeC",
                        new Element("3"),
                        "c"
                ),
                new Item("typeD",
                        new Element("4"),
                        "d"
                ),
                new Item("someWrongContent",
                        new Element("notANumber"),
                        "e"
                )
            );
}
