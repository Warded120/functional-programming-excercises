package com.ihren.exercise4.models;

// or use @UtilityClass
public final class ElementMapper {

    private ElementMapper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Element map(Element element) {
        throw new UnsupportedOperationException("should not be called without mocked static");
    }
}