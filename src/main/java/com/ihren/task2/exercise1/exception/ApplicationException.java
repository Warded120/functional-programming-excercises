package com.ihren.task2.exercise1.exception;

import com.ihren.task2.exercise1.constant.ErrorCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorCode errorCode;

    public ApplicationException(String message) {
        super(message);
        this.errorCode = ErrorCode.UNKNOWN;
    }

    public ApplicationException(ErrorCode errorCode, String message, Exception ex) {
        super(message, ex);
        this.errorCode = errorCode;
    }
}
