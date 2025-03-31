package com.ihren.exercise3;

import com.ihren.exercise3.models.Element;
import com.ihren.exercise3.models.Item;
import com.ihren.exercise3.models.Transaction;

import java.util.List;

public class ModelUtils {
    public static final List<Transaction> transactions =
            List.of(
                    new Transaction("a",
                            new Item("typeA",
                                    new Element("1"),
                                    "a"
                            )
                    ),
                    new Transaction("b",
                            new Item("typeB",
                                    new Element("2"),
                                    "b"
                            )
                    ),
                    new Transaction("c",
                            new Item("typeC",
                                    new Element("3"),
                                    "c"
                            )
                    ),
                    new Transaction("d",
                            new Item("typeD",
                                    new Element("4"),
                                    "d"
                            )
                    ),
                    new Transaction("e",
                            new Item("someWrongContent",
                                    new Element("notANumber"),
                                    "e"
                            )
                    )
            );

    public static final List<Item> items = transactions.stream()
            .map(Transaction::getItem)
            .toList();
}
