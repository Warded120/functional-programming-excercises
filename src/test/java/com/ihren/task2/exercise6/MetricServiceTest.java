package com.ihren.task2.exercise6;

import com.ihren.task2.exercise6.model.Counter;
import com.ihren.task2.exercise6.model.Metrics;
import com.ihren.task2.exercise6.model.Timer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

    private static final String APP_TAG = "app";
    private final String appName = "name";

    @Spy
    @InjectMocks
    private MetricService metricService;

    @Mock
    private Counter messagesReceivedCounter;

    @Mock
    private Counter messagesInDlqCounter;

    @Mock
    private Timer messageProcessingTimer;

    @Mock
    Timer.Sample processTimer;

    @Nested
    class InitTests {
        private MockedStatic<Metrics> metricsMockedStatic;
        private MockedStatic<Timer> timerMockedStatic;

        @BeforeEach
        public void init() {
            metricsMockedStatic = mockStatic(Metrics.class);
            timerMockedStatic = mockStatic(Timer.class);
            ReflectionTestUtils.setField(metricService, "appName", "name");
        }

        @AfterEach
        public void tearDown() {
            metricsMockedStatic.close();
            timerMockedStatic.close();
        }

        @Test
        void should_OK_when_methodsReturnOK() {
            // given
            given(Metrics.counter("total.messages", APP_TAG, appName)).willReturn(messagesReceivedCounter);
            given(Metrics.counter("dlq.messages", APP_TAG, appName)).willReturn(messagesInDlqCounter);
            given(Metrics.timer("timer.process", APP_TAG, appName)).willReturn(messageProcessingTimer);

            // when
            // then
            assertDoesNotThrow(() -> metricService.init());
            metricsMockedStatic.verify(() -> Metrics.counter("total.messages", APP_TAG, appName));
            metricsMockedStatic.verify(() -> Metrics.counter("dlq.messages", APP_TAG, appName));
            metricsMockedStatic.verify(() -> Metrics.timer("timer.process", APP_TAG, appName));
        }

        @Test
        void should_ThrowException_when_CounterMethodThrowsException() {
            // given
            given(Metrics.counter("total.messages", APP_TAG, appName)).willThrow(RuntimeException.class);

            // when
            // then
            assertThrows(RuntimeException.class, () -> metricService.init());
        }

        @Test
        void should_ThrowException_when_Counter2MethodThrowsException() {
            // given

            given(Metrics.counter("total.messages", APP_TAG, appName)).willReturn(messagesReceivedCounter);
            given(Metrics.counter("dlq.messages", APP_TAG, appName)).willThrow(RuntimeException.class);
            given(Metrics.timer("timer.process", APP_TAG, appName)).willReturn(messageProcessingTimer);

            // when
            // then
            assertThrows(RuntimeException.class, () -> metricService.init());
        }

        @Test
        void should_ThrowException_when_TimerMethodThrowsException() {
            // given
            given(Metrics.counter("total.messages", APP_TAG, appName)).willReturn(messagesReceivedCounter);
            given(Metrics.counter("dlq.messages", APP_TAG, appName)).willReturn(messagesInDlqCounter);
            given(Metrics.timer("timer.process", APP_TAG, appName)).willThrow(RuntimeException.class);

            // when
            // then
            assertThrows(RuntimeException.class, () -> metricService.init());
        }

        @Test
        void should_MessagesReceivedCounterBeNull_when_MetricsReturnNull() {
            // given
            given(Metrics.counter("total.messages", APP_TAG, appName)).willReturn(null);

            // when
            metricService.init();

            // then
            Counter actual = (Counter) ReflectionTestUtils.getField(metricService, "messagesReceivedCounter");
            assertNull(actual);
        }

        @Test
        void should_MessagesInDlqCounterBeNull_when_MetricsReturnNull() {
            // given
            given(Metrics.counter("dlq.messages", APP_TAG, appName)).willReturn(null);

            // when
            metricService.init();

            // then
            Counter actual = (Counter) ReflectionTestUtils.getField(metricService, "messagesInDlqCounter");
            assertNull(actual);
        }

        @Test
        void should_MessageProcessingTimerBeNull_when_MetricsReturnNull() {
            // given
            given(Metrics.timer("timer.process", APP_TAG, appName)).willReturn(null);

            // when
            metricService.init();

            // then
            Timer actual = (Timer) ReflectionTestUtils.getField(metricService, "messageProcessingTimer");
            assertNull(actual);
        }
    }

    @Nested
    class IncrementMessagesReceivedCounterTests {
        @Test
        void should_IncrementMessagesReceivedCounter_when_MethodIsCalled() {
            // when
            metricService.incrementMessagesReceivedCounter();

            // then
            then(messagesReceivedCounter).should().increment();
        }

        @Test
        void should_ThrowRuntimeException_when_MethodThrowsRuntimeException() {
            //given
            willThrow(RuntimeException.class).given(metricService).incrementMessagesReceivedCounter();

            // when
            // then
            assertThrows(RuntimeException.class, () -> metricService.incrementMessagesReceivedCounter());
            then(metricService).should().incrementMessagesReceivedCounter();
            then(messagesReceivedCounter).should(never()).increment();
        }
    }

    @Nested
    class IncrementMessagesInDlqCounterTests {
        @Test
        void should_incrementMessagesInDlqCounter_when_MethodIsCalled() {
            // when
            metricService.incrementMessagesInDlqCounter();

            // then
            then(messagesInDlqCounter).should().increment();
        }

        @Test
        void should_ThrowRuntimeException_when_MethodThrowsRuntimeException() {
            //given
            willThrow(RuntimeException.class).given(metricService).incrementMessagesInDlqCounter();

            // when
            // then
            assertThrows(RuntimeException.class, () -> metricService.incrementMessagesInDlqCounter());
            then(metricService).should().incrementMessagesInDlqCounter();
            then(messagesInDlqCounter).should(never()).increment();
        }
    }

    @Nested
    class StopProcessingTimerTests {
        @Test
        void should_incrementMessagesInDlqCounter_when_MethodIsCalled() {
            // when
            metricService.stopProcessingTimer(processTimer);

            // then
            then(processTimer).should().stop(messageProcessingTimer);
        }

        @Test
        void should_ThrowRuntimeException_when_MethodThrowsRuntimeException() {
            //given
            willThrow(RuntimeException.class).given(metricService).stopProcessingTimer(processTimer);

            // when
            // then
            assertThrows(RuntimeException.class, () -> metricService.stopProcessingTimer(processTimer));
            then(metricService).should().stopProcessingTimer(processTimer);
            then(processTimer).should(never()).stop(messageProcessingTimer);
        }
    }
}