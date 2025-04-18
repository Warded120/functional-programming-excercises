package com.ihren.task2.exercise1;

import com.ihren.task2.exercise1.annotation.Component;
import com.ihren.task2.exercise1.constant.ErrorCode;
import com.ihren.task2.exercise1.exception.ApplicationException;
import com.ihren.task2.exercise1.exception.MappingException;
import com.ihren.task2.exercise1.handler.DLQHandler;
import com.ihren.task2.exercise1.service.Metrics;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandler {
    private final DLQHandler dlqHandler;
    private final Metrics metrics;

    public <I, O> Try<O> handle(Function<I, O> func, I input) {
        Function<ApplicationException, Try<O>> handleFunction = ex -> {
            log.error("An error occurred while processing the message", ex);
            dlqHandler.send(input, ex);
            metrics.incrementMessagesInDlqCounter();
            return Try.success(null);
        };

        return Try.of(() -> func.apply(input))
                .recoverWith(MappingException.class, handleFunction::apply)
                .recoverWith(Exception.class, ex -> handleFunction.apply(new ApplicationException(ErrorCode.UNKNOWN, ex.getMessage(), ex)));
    }
}