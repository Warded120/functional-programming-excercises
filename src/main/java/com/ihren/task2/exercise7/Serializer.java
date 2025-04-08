package com.ihren.task2.exercise7;

import java.util.Map;

public interface Serializer<T> {
    void configure(Map<String, ?> configs, boolean isKey);
    byte[] serialize(String topic, T data);
}
