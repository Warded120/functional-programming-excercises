package com.ihren.exercise4;

import com.ihren.exercise4.models.DateTimeUtils;
import com.ihren.exercise4.models.ElementMapper;
import com.ihren.exercise4.models.Item;
import com.ihren.exercise4.models.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        return Stream.ofNullable(transaction)
                .map(Transaction::items)
                .flatMap(List::stream)
                .filter(item ->
                            Optional.ofNullable(item)
                                    .flatMap(itemOpt -> Optional.ofNullable(itemOpt.startDateTime()))
                                    .flatMap(localDateTime -> Optional.ofNullable(item.element()))
                                    .map(ElementMapper::map)
                                    .isPresent()
                )
                .map(item -> {
                    Optional.ofNullable(item.startDateTime())
                            .ifPresent(startDateTime ->
                                item.element().setStartDateTime(DateTimeUtils.getInstant(startDateTime))
                            );
                    return item;
                })
                .toList();
    }
}
