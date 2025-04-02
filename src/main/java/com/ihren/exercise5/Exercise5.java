package com.ihren.exercise5;

import com.ihren.exercise5.models.Item;
import com.ihren.exercise5.models.Sale;
import com.ihren.exercise5.models.Transaction;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Exercise5 {
//    public List<Item> convert(Transaction transaction) {
//        List<Item> items = new ArrayList<>();
//        List<Item> filteredItems = filterItems(transaction);
//        for (Item filteredItem : filteredItems) {
//            Optional.ofNullable(filteredItem.getSale())
//                    .ifPresent(sale -> {
//                        if (!filterSale(sale)) {
//                            Item newItem = createItem(filteredItem);
//                            items.add(newItem);
//                        }
//                    });
//
//            Optional.ofNullable(filteredItem.getAReturn())
//                    .ifPresent(returnItem -> {
//                        if (isReturnTransaction(transaction)) {
//                            Optional.ofNullable(filteredItem.getData())
//                                    .ifPresent(data -> Optional.ofNullable(data.getType())
//                                            .ifPresent(type -> {
//                                                if (type.toString().equals("SOME_TYPE")) {
//                                                    Item newItem = createItem(filteredItem);
//                                                    items.add(newItem);
//                                                }
//                                            }));
//                        }
//                    });
//
//            Optional.ofNullable(filteredItem.getFuelSale())
//                    .ifPresent(fuelSale -> {
//                        Item newItem = createItem(filteredItem);
//                        items.add(newItem);
//                    });
//        }
//        return items;
//    }

    private final String someType = "SOME_TYPE";

    public List<Item> convert(Transaction transaction) {
        return Stream.ofNullable(transaction)
                .map(Transaction::items)
                .map(this::filterItems)
                .flatMap(List::stream)
                .filter(item ->
                        Optional.ofNullable(item.sale())
                                .filter(this::filterSale)
                                .isPresent() ||
                        Optional.ofNullable(item.aReturn())
                                .filter(aReturn -> isReturnTransaction(transaction))
                                .flatMap(aReturn -> Optional.ofNullable(item.data()))
                                .flatMap(data -> Optional.ofNullable(data.type()))
                                .filter(type -> type.equals(someType))
                                .isPresent() ||
                        Optional.ofNullable(item.fuelSale())
                                .isPresent()
                )
                .map(this::createItem)
                .filter(Objects::nonNull)
                .toList();
    }

    protected List<Item> filterItems(List<Item> item) {
        throw new UnsupportedOperationException("Should not be called without mocked static");
    }

    protected boolean filterSale(Sale sale) {
        throw new UnsupportedOperationException("Should not be called without mocked static");
    }

    protected boolean isReturnTransaction(Transaction transaction) {
        throw new UnsupportedOperationException("Should not be called without mocked static");
    }

    protected Item createItem(Item item) {
        throw new UnsupportedOperationException("Should not be called without mocked static");
    }
}
