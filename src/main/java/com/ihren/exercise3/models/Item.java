package com.ihren.exercise3.models;

public record Item (
    CharSequence type,
    Element element,
    String transactionId
) {}
