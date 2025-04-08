package com.ihren.task2.exercise5.cache;

import java.util.function.Function;

public class GenericCache<T, R> {
    public Function<T, R> of(Function<T, R> func) {
        throw new UnsupportedOperationException("Should not be called without mocking");
    }
}