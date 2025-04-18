package com.ihren.task2.exercise2;

import com.ihren.task2.exercise2.annotation.Component;
import com.ihren.task2.exercise2.exception.BusinessException;
import com.ihren.task2.exercise2.model.Item;
import com.ihren.task2.exercise2.model.Transaction;
import com.ihren.task2.exercise2.mapper.TransactionMapper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TransactionConverter {

    private final TransactionMapper transactionMapper;
    private final Item posLogRoot;

    public Transaction convert(Item item, Map<String, Object> headers) {
        return Try.of(() -> transactionMapper.map(item.element(), headers))
            .recoverWith(
                    BusinessException.class,
                    ex -> Try.failure(new BusinessException(
                        ex.getMessage(),
                        posLogRoot.element().transactionId().toString(),
                        ex.getCause()
                    ))
            )
            .get();
    }
}