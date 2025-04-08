package com.ihren.task2.exercise3.model;

import java.time.Instant;

public record Element(
        Instant startDate,
        Instant endDate,
        String data,
        String award
) { }
