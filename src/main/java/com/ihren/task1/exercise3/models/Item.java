package com.ihren.task1.exercise3.models;

public record Item(
    CharSequence type,
    Element element,
    String transactionId
) { }
