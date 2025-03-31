package com.ihren.exercise3.models;

public class SomeWrongType {
    public static boolean contentEquals(String content) {
        throw new RuntimeException("should not be called without mocked static");
    }
}
