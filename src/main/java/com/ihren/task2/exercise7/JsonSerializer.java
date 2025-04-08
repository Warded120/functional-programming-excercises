package com.ihren.task2.exercise7;

import com.ihren.task2.exercise7.exception.SerializingException;
import com.ihren.task2.exercise7.mapper.ObjectMapper;
import com.ihren.task2.exercise7.util.JacksonConfig;
import io.vavr.control.Try;

import java.util.Map;
import java.util.Optional;

public class JsonSerializer<T> implements Serializer<T> {

    private ObjectMapper objectMapper;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        objectMapper = new ObjectMapper();
        JacksonConfig.initConfigs(objectMapper);
    }

    @Override
    public byte[] serialize(String topic, T data) {
        return Optional.ofNullable(data)
                .map(item ->
                        Try.of(() -> objectMapper.writeValueAsBytes(data))
                                .recoverWith(ex -> Try.failure(new SerializingException("Error serializing JSON message", ex)))
                                .get()
                )
                .orElse(null);
    }
}