package com.ihren.task2.exercise3.model;

public record Element(
        String type,
        Amount amount
) {
    public record Amount(
            String currencyCode
    ) { }
}