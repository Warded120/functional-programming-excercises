package com.ihren.exercise7.models;

public record Item (
    Long id,
    Long parentId,
    Boolean isCancelled,
    Action action,
    String returnReason
) {}
