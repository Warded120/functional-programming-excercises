package com.ihren.task2.exercise2;

import com.ihren.task2.exercise2.exception.BusinessException;
import com.ihren.task2.exercise2.mapper.TransactionMapper;
import com.ihren.task2.exercise2.model.Element;
import com.ihren.task2.exercise2.model.Item;
import com.ihren.task2.exercise2.model.Transaction;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionConverterTest {

    @InjectMocks
    private TransactionConverter transactionConverter;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private Item posLogRoot;

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(mock(Transaction.class)),
                Arguments.of((Object) null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void should_ReturnTransaction_when_InputIsValid(Transaction expected) {
        //given
        Item itemMock = mock(Item.class);
        Element elementMock = mock(Element.class);
        Map<String, Object> headersMock = mock(Map.class);

        given(itemMock.element()).willReturn(elementMock);
        given(transactionMapper.map(elementMock, headersMock)).willReturn(expected);

        //when
        Transaction actual = transactionConverter.convert(itemMock, headersMock);

        //then
        assertEquals(expected, actual);
        then(itemMock).should().element();
        then(transactionMapper).should().map(elementMock, headersMock);
    }

    @Test
    void should_ThrowRuntimeException_when_InputIsInvalid() {
        //given
        Item itemMock = mock(Item.class);
        Element elementMock = mock(Element.class);
        Map<String, Object> headersMock = mock(Map.class);

        given(itemMock.element()).willReturn(elementMock);
        willThrow(BusinessException.class).given(transactionMapper).map(elementMock, headersMock);
        given(posLogRoot.element()).willReturn(elementMock);
        given(elementMock.transactionId()).willReturn(1);

        //when
        //then
        assertThrows(BusinessException.class, () -> transactionConverter.convert(itemMock, headersMock));
        then(itemMock).should().element();
        then(transactionMapper).should().map(elementMock, headersMock);
    }
}