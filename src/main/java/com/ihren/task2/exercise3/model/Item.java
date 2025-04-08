package com.ihren.task2.exercise3.model;

import java.time.LocalDateTime;

public record Item(
        LocalDateTime startDate,
        Long duration,
        String data,
        Double award
) { }
