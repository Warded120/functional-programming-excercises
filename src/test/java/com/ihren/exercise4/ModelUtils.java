package com.ihren.exercise4;

import com.ihren.exercise4.models.Element;
import com.ihren.exercise4.models.Item;
import com.ihren.exercise4.models.Transaction;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class ModelUtils {
    public static final Instant INSTANT = Instant.now();
    public static final LocalDateTime DATE_TIME = LocalDateTime.now();

    public static final Transaction TRANSACTION = new Transaction(
            Stream.of(
                    null,
                    new Item(null, DATE_TIME),
                    new Item(new Element(null), DATE_TIME),
                    new Item(new Element(null), null),
                    new Item(new Element(INSTANT), DATE_TIME),
                    new Item(new Element(INSTANT), DATE_TIME)
            )
            .toList()
    );
}
