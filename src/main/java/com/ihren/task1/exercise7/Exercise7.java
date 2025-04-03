package com.ihren.task1.exercise7;

import com.ihren.task1.exercise7.models.Item;
import com.ihren.task1.exercise7.models.Action;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Exercise7 {
//    public List<Item> filter(List<Item> items) {
//        List<Item> filteredItems = new ArrayList<>();
//        Set<Long> parentItemIds = new HashSet<>();
//        for (Item item : items) {
//            Optional.ofNullable(item.isCancelled())
//                    .ifPresent(flag -> {
//                        if (Boolean.TRUE.equals(flag)) {
//                            filteredItems.add(item);
//                            if (item.parentId() != null) {
//                                parentItemIds.add(item.parentId());
//                            }
//                        }
//                    });
//            Optional.ofNullable(item.action())
//                    .ifPresent(action -> {
//                        if (action.toString().equalsIgnoreCase("SKIPPED")) {
//                            filteredItems.add(item);
//                        }
//                    });
//            Optional.ofNullable(item.returnReason())
//                    .ifPresent(returnReason -> filteredItems.add(item));
//        }
//        for (Item item : items) {
//            if (parentItemIds.contains(item.id())) {
//                filteredItems.add(item);
//            }
//        }
//        List<Item> result = new ArrayList<>(items);
//        result.removeAll(filteredItems);
//        return result;
//    }

    public List<Item> filter(List<Item> items) {
        List<Long> parentIds = Stream.ofNullable(items)
                .flatMap(List::stream)
                .filter(item ->
                        Optional.ofNullable(item.isCancelled())
                                .filter(Boolean.TRUE::equals)
                                .flatMap(flag -> Optional.ofNullable(item.parentId()))
                                .isPresent()
                )
                .map(Item::parentId)
                .distinct()
                .toList();

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
                            .filter(action -> action.equals(Action.SKIPPED))
                            .isPresent() ||
                        Optional.ofNullable(item.returnReason())
                            .isPresent() ||
                        Optional.ofNullable(item.id())
                            .filter(parentIds::contains)
                            .isPresent()
                    )
                )
                .toList();
    }

}
