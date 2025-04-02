package com.ihren.exercise3;

import com.ihren.exercise3.models.Item;
import io.vavr.control.Try;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Exercise3 {
    private static final int ID_UPPER_BOUND = 7;
    private static final int ID_LOWER_BOUND = 2;
    private static final String SOME_WRONG_TYPE = "SOME_WRONG_TYPE";
//    public List<Item> filter(List<Item> items) {
//        return items.stream()
//                .filter(item -> !SomeWrongType.contentEquals(item.getType()))
//                .filter(item -> {
//                    CharSequence elementId = getElementId(item.getTransactionId());
//                    if (Objects.nonNull(elementId)) {
//                        long id = Long.parseLong(elementId.toString());
//                        return id <= 7 && id >= 2;
//                    }
//                    return true;
//                })
//                .collect(Collectors.toList());
//    }

    public List<Item> filter(List<Item> items) {
        return Stream.ofNullable(items)
                .flatMap(List::stream)
                .filter(item -> !SOME_WRONG_TYPE.contentEquals(item.type()))
                .filter(item ->
                    Optional.ofNullable(getElementId(item.transactionId()))
                            .map(str -> Try.of(() -> Long.parseLong(str)).getOrElse((Long) null))
                            .filter(id -> id <= ID_UPPER_BOUND && id >= ID_LOWER_BOUND)
                            .isPresent()
                )
                .toList();
    }

    protected String getElementId(String transactionId) {
        throw new UnsupportedOperationException("Should not be called without mocking");
    }
}
