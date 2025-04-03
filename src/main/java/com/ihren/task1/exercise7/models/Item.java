package com.ihren.task1.exercise7.models;

public record Item (
    Long id,
    Long parentId,
    Boolean isCancelled,
    Action action,
    String returnReason
) {}
