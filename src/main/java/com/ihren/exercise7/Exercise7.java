package com.ihren.exercise7;

import com.ihren.exercise7.models.*;

import java.util.*;
import java.util.stream.Stream;

import static com.ihren.exercise7.models.Action.SKIPPED;

public class Exercise7 {
//    public List<Item> filter(List<Item> items) {
//        List<Item> filteredItems = new ArrayList<>();
//        Set<Long> parentItemIds = new HashSet<>();
//        for (Item item : items) {
//            Optional.ofNullable(item.getIsCancelled())
//                    .ifPresent(flag -> {
//                        if (Boolean.TRUE.equals(flag)) {
//                            filteredItems.add(item);
//                            if (item.getParentId() != null) {
//                                parentItemIds.add(item.getParentId());
//                            }
//                        }
//                    });
//            Optional.ofNullable(item.getAction())
//                    .ifPresent(action -> {
//                        if (action.toString().equalsIgnoreCase("SKIPPED")) {
//                            filteredItems.add(item);
//                        }
//                    });
//            Optional.ofNullable(item.getReturnReason())
//                    .ifPresent(returnReason -> filteredItems.add(item));
//        }
//        for (Item item : items) {
//            if (parentItemIds.contains(item.getId())) {
//                filteredItems.add(item);
//            }
//        }
//        List<Item> result = new ArrayList<>(items);
//        result.removeAll(filteredItems);
//        return result;
//    }

    public List<Item> filter(List<Item> items) {
        return Stream.ofNullable(items)
                .flatMap(List::stream)
                .filter(item ->
                    !(
                        Optional.ofNullable(item.isCancelled())
                            .orElse(false) ||
                        Optional.ofNullable(item.isCancelled())
                            .flatMap(isCancelled -> Optional.ofNullable(item.parentId()))
                            .filter(pId -> Optional.ofNullable(item.id()).equals(pId))
                            .isPresent() ||
                        Optional.ofNullable(item.action())
                            .filter(action -> action.equals(SKIPPED))
                            .isPresent() ||
                        Optional.ofNullable(item.returnReason())
                            .isPresent()
                    )
                )
                .toList();
    }

}
