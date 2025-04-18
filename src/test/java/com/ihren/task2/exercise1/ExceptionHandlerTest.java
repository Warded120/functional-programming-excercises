package com.ihren.task2.exercise1;

import com.ihren.task2.exercise1.constant.ErrorCode;
import com.ihren.task2.exercise1.exception.ApplicationException;
import com.ihren.task2.exercise1.exception.MappingException;
import com.ihren.task2.exercise1.handler.DLQHandler;
import com.ihren.task2.exercise1.model.Metrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerTest {

    @InjectMocks
    private ExceptionHandler exceptionHandler;

    @Mock
    private Metrics metrics;

    @Mock
    private DLQHandler dlqHandler;

    @Test
    void should_ReturnOK_when_InputIsValid() {
        //given
        String input = "test";
        String expected = "test";

        Function<String, String> func = mock(Function.class);

        given(func.apply(input)).willReturn(expected);

        //when
        String actual = exceptionHandler.handle(func, input).get();

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_ReturnNull_when_RecoverFromMappingException() {
        //given
        String input = "invalid";

        Function<String, Integer> func = mock(Function.class);

        given(func.apply(input)).willThrow(MappingException.class);

        //when
        Integer actual = exceptionHandler.handle(func, input).get();

        //then
        assertNull(actual);
        then(dlqHandler).should().send(eq(input), any(MappingException.class));
        then(metrics).should().incrementMessagesInDlqCounter();
    }

    @Test
    void should_ReturnNull_when_RecoverFromException() {
        //given
        String input = "invalid";

        Function<String, Integer> func = mock(Function.class);

        given(func.apply(input)).willThrow(RuntimeException.class);

        //when
        Integer actual = exceptionHandler.handle(func, input).get();

        //then
        ArgumentCaptor<ApplicationException> exCaptor = ArgumentCaptor.forClass(ApplicationException.class);

        assertNull(actual);
        then(dlqHandler).should().send(eq(input), exCaptor.capture());

        ApplicationException exception = exCaptor.getValue();
        assertAll(
                () -> assertEquals(ErrorCode.UNKNOWN, exception.getErrorCode()),
                () -> assertInstanceOf(Exception.class, exception.getCause())
        );

        then(metrics).should().incrementMessagesInDlqCounter();
    }
}