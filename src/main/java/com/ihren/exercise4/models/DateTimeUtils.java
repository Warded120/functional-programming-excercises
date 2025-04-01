package com.ihren.exercise4.models;

import java.time.Instant;
import java.time.LocalDateTime;

// or use @UtilityClass
public final class DateTimeUtils {

    private DateTimeUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Instant getInstant(LocalDateTime localDateTime) {
        throw new UnsupportedOperationException("Should not be called without mocked static");
    }
}
