package com.ihren.task2.exercise2;

import com.ihren.task2.exercise2.exception.BusinessException;
import com.ihren.task2.exercise2.mapper.TransactionMapper;
import com.ihren.task2.exercise2.model.Element;
import com.ihren.task2.exercise2.model.Item;
import com.ihren.task2.exercise2.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TransactionConverterTest {

    @InjectMocks
    private TransactionConverter transactionConverter;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private Item posLogRoot;

    @Test
    void should_ReturnTransaction_when_InputIsValid() {
        //given
        Transaction expected = mock(Transaction.class);
        Item item = mock(Item.class);
        Element element = mock(Element.class);
        Map<String, Object> headers = mock(Map.class);

        given(item.element()).willReturn(element);
        given(transactionMapper.map(element, headers)).willReturn(expected);

        //when
        Transaction actual = transactionConverter.convert(item, headers);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_ThrowException_when_InputIsInvalid() {
        //given
        Item item = mock(Item.class);
        Element element = mock(Element.class);
        Map<String, Object> headers = mock(Map.class);

        given(item.element()).willReturn(element);
        given(posLogRoot.element()).willReturn(element);
        given(element.transactionId()).willReturn(1);
        willThrow(BusinessException.class).given(transactionMapper).map(element, headers);

        //when
        //then
        BusinessException businessException =
                assertThrows(BusinessException.class, () -> transactionConverter.convert(item, headers));
        assertEquals(element.transactionId().toString(), businessException.getTransactionId());
    }

    //TODO: do i need this test?
    @Test
    void should_ThrowOtherException_when_MapThrowsException() {
        //given
        Item item = mock(Item.class);
        Element element = mock(Element.class);
        Map<String, Object> headers = mock(Map.class);

        given(item.element()).willReturn(element);
        willThrow(RuntimeException.class).given(transactionMapper).map(element, headers);

        //when
        //then
        assertThrows(RuntimeException.class, () -> transactionConverter.convert(item, headers));
    }
}