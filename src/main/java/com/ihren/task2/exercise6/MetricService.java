package com.ihren.task2.exercise6;

import com.ihren.task2.exercise6.annotation.Component;
import com.ihren.task2.exercise6.annotation.PostConstruct;
import com.ihren.task2.exercise6.annotation.Value;
import com.ihren.task2.exercise6.model.Counter;
import com.ihren.task2.exercise6.model.Timer;
import com.ihren.task2.exercise6.model.Metrics;

@Component
public class MetricService {
    private static final String APP_TAG = "app";

    @Value("${spring.application.name}")
    private String appName;

    private Counter messagesReceivedCounter;
    private Counter messagesInDlqCounter;
    private Timer messageProcessingTimer;

    @PostConstruct
    public void init() {
        messagesReceivedCounter = Metrics.counter("total.messages", APP_TAG, appName);
        messagesInDlqCounter = Metrics.counter("dlq.messages", APP_TAG, appName);
        messageProcessingTimer = Metrics.timer("timer.process", APP_TAG, appName);
    }

    public void incrementMessagesReceivedCounter() {
        messagesReceivedCounter.increment();
    }

    public void incrementMessagesInDlqCounter() {
        messagesInDlqCounter.increment();
    }

    public void stopProcessingTimer(Timer.Sample processTimer) {
        processTimer.stop(messageProcessingTimer);
    }
}