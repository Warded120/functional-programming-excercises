package com.ihren.task2.exercise2.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String transactionId;
    public BusinessException(String message, String transactionId, Throwable cause) {
        super(message, cause);
        this.transactionId = transactionId;
    }
}
