package com.ihren.task2.exercise1;

import com.ihren.task2.exercise1.exception.ApplicationException;
import com.ihren.task2.exercise1.exception.MappingException;
import com.ihren.task2.exercise1.handler.DLQHandler;
import com.ihren.task2.exercise1.model.Metrics;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerTest {

    @InjectMocks
    private ExceptionHandler exceptionHandler;

    @Mock
    private Metrics metrics;

    @Mock
    private DLQHandler dlqHandler;

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of("test", 4),
                Arguments.of(4, "test"),
                Arguments.of(List.of(0, 0), 2),
                Arguments.of(2, List.of(0, 0)),
                Arguments.of(null, null)
        );
    };

    @ParameterizedTest
    @MethodSource("dataProvider")
    <I, E> void should_ReturnOK_when_InputIsValid( I input, E expected) {
        //given
        Try<E> tryOfExpected = Try.success(expected);

        Function<I, E> func = mock(Function.class);

        given(func.apply(input)).willReturn(expected);

        //when
        Try<E> actual = exceptionHandler.handle(func, input);

        //then
        assertEquals(tryOfExpected, actual);
        then(func).should().apply(input);
    }

    @Test
    void should_ReturnNull_when_RecoverFromMappingException() {
        //given
        String input = "invalid";
        Try<Object> expected = Try.success(null);

        Function<String, Integer> func = mock(Function.class);

        given(func.apply(input)).willThrow(MappingException.class);
        willDoNothing().given(dlqHandler).send(eq(input), any(MappingException.class));
        willDoNothing().given(metrics).incrementMessagesInDlqCounter();

        //when
        Try<Integer> actual = exceptionHandler.handle(func, input);

        //then
        assertEquals(expected, actual);
        then(func).should().apply(input);
        then(dlqHandler).should().send(eq(input), any(MappingException.class));
        then(metrics).should().incrementMessagesInDlqCounter();
    }

    @Test
    void should_ReturnNull_when_RecoverFromException() {
        //given
        String input = "invalid";
        Try<Object> expected = Try.success(null);

        Function<String, Integer> func = mock(Function.class);

        given(func.apply(input)).willThrow(RuntimeException.class);
        willDoNothing().given(dlqHandler).send(eq(input), any(ApplicationException.class));
        willDoNothing().given(metrics).incrementMessagesInDlqCounter();

        //when
        Try<Integer> actual = exceptionHandler.handle(func, input);

        //then
        assertEquals(expected, actual);
        then(func).should().apply(input);
        then(dlqHandler).should().send(eq(input), any(ApplicationException.class));
        then(metrics).should().incrementMessagesInDlqCounter();
    }

    @Test
    void should_ThrowException_when_dqlHandlerThrowsException() {
        //given
        String input = "invalid";
        Function<String, Integer> func = mock(Function.class);

        given(func.apply(input)).willThrow(RuntimeException.class);
        willThrow(RuntimeException.class).given(dlqHandler).send(eq(input), any(ApplicationException.class));

        //when
        Try<Integer> actual = exceptionHandler.handle(func, input);

        //then
        assertAll(
                () -> assertTrue(actual.isFailure()),
                () -> assertInstanceOf(RuntimeException.class, actual.getCause())
        );
        then(func).should().apply(input);
        then(dlqHandler).should().send(eq(input), any(ApplicationException.class));
    }

    @Test
    void should_ThrowException_when_MetricsThrowsException() {
        //given
        String input = "invalid";
        Function<String, Integer> func = mock(Function.class);

        given(func.apply(input)).willThrow(RuntimeException.class);
        willDoNothing().given(dlqHandler).send(eq(input), any(ApplicationException.class));
        willThrow(RuntimeException.class).given(metrics).incrementMessagesInDlqCounter();

        //when
        Try<Integer> actual = exceptionHandler.handle(func, input);

        //then
        assertAll(
                () -> assertTrue(actual.isFailure()),
                () -> assertInstanceOf(RuntimeException.class, actual.getCause())
        );
        then(func).should().apply(input);
        then(dlqHandler).should().send(eq(input), any(ApplicationException.class));
        then(metrics).should().incrementMessagesInDlqCounter();
    }
}