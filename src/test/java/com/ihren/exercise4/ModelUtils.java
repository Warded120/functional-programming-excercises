package com.ihren.exercise4;

import com.ihren.exercise4.models.Element;
import com.ihren.exercise4.models.Item;
import com.ihren.exercise4.models.Transaction;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ModelUtils {
    public static final Transaction TRANSACTION;
    public static final Instant instant = Instant.now();
    public static final LocalDateTime dateTime = LocalDateTime.now();

    static {
        List<Item> items = new ArrayList<>();
        items.add(null);
        items.add(new Item(null, dateTime));
        items.add(new Item(new Element(null), dateTime));
        items.add(new Item(new Element(null), null));
        items.add(new Item(new Element(instant), dateTime));
        items.add(new Item(new Element(instant), dateTime));
        TRANSACTION = new Transaction(items);
    }
}
