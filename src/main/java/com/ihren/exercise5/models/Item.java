package com.ihren.exercise5.models;

public record Item (
    Sale sale,
    String fuelSale,
    Return aReturn,
    Data data
) {}
