package com.ihren.exercise5;

import com.ihren.exercise5.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        return Optional.ofNullable(transaction.items())
                .orElseGet(List::of)
                .stream()
                .filter(item ->
                    filterItem(item) &&
                    (
                        (
                                Optional.ofNullable(item.sale())
                                        .filter(Exercise5::filterSale)
                                        .isPresent()
                        ) || (
                            Optional.ofNullable(item.aReturn()).isPresent() &&
                            isReturnTransaction(transaction) &&
                            Optional.ofNullable(item.data())
                                .map(Data::type)
                                .filter(type -> type.equals(someType))
                                .isPresent()
                        ) || (
                            Optional.ofNullable(item.fuelSale()).isPresent()
                        )
                    )
                )
                .map(Exercise5::createItem)
                .toList();
    }


    public static boolean filterItem(Item item) {
        throw new UnsupportedOperationException("Should not be called without mocked static");
    }

    public static boolean filterSale(Sale sale) {
        throw new UnsupportedOperationException("Should not be called without mocked static");
    }

    public static boolean isReturnTransaction(Transaction transaction) {
        throw new UnsupportedOperationException("Should not be called without mocked static");
    }

    public static Item createItem(Item item) {
        throw new UnsupportedOperationException("Should not be called without mocked static");
    }
}
