package com.ihren.exercise3.models;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Transaction {
    private String id;
    private final Item item;

    public static final List<Transaction> TRANSACTIONS =
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

    public static final List<Item> ITEMS = TRANSACTIONS.stream()
            .map(Transaction::getItem)
            .toList();
}
