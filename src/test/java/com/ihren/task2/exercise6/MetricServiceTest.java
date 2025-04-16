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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

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

    @Nested
    class InitTests {
        private MockedStatic<Metrics> metricsMockedStatic;

        @BeforeEach
        public void init() {
            metricsMockedStatic = mockStatic(Metrics.class);
            ReflectionTestUtils.setField(metricService, "appName", "name");
        }

        @AfterEach
        public void tearDown() {
            metricsMockedStatic.close();
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

    @Test
    void should_IncrementMessagesReceivedCounter_when_MethodIsCalled() {
        // when
        metricService.incrementMessagesReceivedCounter();

        // then
        then(messagesReceivedCounter).should().increment();
    }

    @Test
    void should_incrementMessagesInDlqCounter_when_MethodIsCalled() {
        // when
        metricService.incrementMessagesInDlqCounter();

        // then
        then(messagesInDlqCounter).should().increment();
    }

    @Test
    void should_stopProcessingTimer_when_MethodIsCalled() {
        //given
        Timer.Sample processTimer = mock(Timer.Sample.class);

        // when
        metricService.stopProcessingTimer(processTimer);

        // then
        then(processTimer).should().stop(messageProcessingTimer);
    }
}