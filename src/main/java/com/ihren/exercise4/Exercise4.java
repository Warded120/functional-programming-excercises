package com.ihren.exercise4;

import com.ihren.exercise4.models.DateTimeUtils;
import com.ihren.exercise4.models.ElementMapper;
import com.ihren.exercise4.models.Item;
import com.ihren.exercise4.models.Transaction;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Exercise4 {
//    public List<Item> convert(Transaction transaction) {
//        List<Item> items = new ArrayList<>();
//        for (Item item : getItems(transaction)) {
//            Optional.ofNullable(item.getElement())
//                    .flatMap(ElementMapper::map)
//                    .ifPresent(element -> {
//                        Optional.ofNullable(item.getStartDateTime())
//                                .ifPresent(startDateTime -> {
//                                    element.setStartDateTime(DateTimeUtils.getInstant(startDateTime));
//                                });
//                        items.add(item);
//                    });
//        }
//        return items;
//    }

    public List<Item> convert(Transaction transaction) {
        return Optional.ofNullable(transaction)
                .map(Transaction::items)
                .stream()
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .filter(item ->
                    Optional.of(item)
                        .map(Item::startDateTime)
                        .flatMap(localDateTime -> Optional.ofNullable(item.element()))
                        .flatMap(ElementMapper::map)
                        .map(element -> {
                            Optional.ofNullable(item.startDateTime())
                                .ifPresent(startDateTime ->
                                    element.setStartDateTime(DateTimeUtils.getInstant(startDateTime))
                                );
                            return element;
                        })
                        .isPresent()
                )
                .toList();
    }
}
