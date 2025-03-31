package com.ihren.exercise3;

import com.ihren.exercise3.models.Item;
import com.ihren.exercise3.models.SomeWrongType;
import com.ihren.exercise3.models.Transaction;

import java.util.List;
import java.util.Optional;

public class Exercise3 {
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
        return items.stream()
                .filter(item ->
                    !SomeWrongType.contentEquals(item.getType()) &&
                    Optional.of(getElementId(item.getTransactionId()))
                            .map(Long::parseLong)
                            .filter(id -> id <= 7 && id >= 2)
                            .isPresent()
                )
                .toList();
    }

    public String getElementId(String transactionId) {
        return Transaction.TRANSACTIONS.stream()
                .filter(transaction -> transaction.getId().equals(transactionId)).findFirst()
                .orElseThrow(() -> new NullPointerException("No transaction with id " + transactionId))
                .getItem()
                .getElement()
                .getId();
    }
}
