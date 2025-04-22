package com.ihren.task2.exercise1.handler;

import com.ihren.task2.exercise1.exception.ApplicationException;

public class DLQHandler {
    public <I> void send(I input, ApplicationException ex) {
        throw new UnsupportedOperationException("Should not be called without mocking");
    }
}
