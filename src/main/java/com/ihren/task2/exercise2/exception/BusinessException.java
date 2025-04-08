package com.ihren.task2.exercise2.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message, String transactionId, Throwable cause) {
        super(message + ": " + transactionId, cause);
    }

    public BusinessException() { }
}
