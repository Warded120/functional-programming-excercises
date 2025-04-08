package com.ihren.task2.exercise7.exception;

public class SerializingException extends RuntimeException {
    public SerializingException(String message, Throwable ex) {
        super(message, ex);
    }
}
