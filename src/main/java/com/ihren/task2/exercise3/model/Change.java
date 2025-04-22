package com.ihren.task2.exercise3.model;

import java.math.BigDecimal;

public record Change(
            String currencyCode,
            BigDecimal amount
) { }