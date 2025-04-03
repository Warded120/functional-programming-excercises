package com.ihren.task1.exercise4.models;

import java.time.LocalDateTime;

public record Item(
    Element element,
    LocalDateTime startDateTime
) { }
