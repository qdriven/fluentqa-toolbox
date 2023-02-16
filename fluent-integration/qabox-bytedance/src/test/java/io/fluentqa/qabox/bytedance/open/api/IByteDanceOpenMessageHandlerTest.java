package io.fluentqa.qabox.bytedance.open.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.fluentqa.qabox.bytedance.open.message.handler.LogHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IByteDanceOpenMessageHandlerTest {

    @Test
    void handle() {

    }

    @Test
    void getHandlerName() {
        Assertions.assertEquals("LogHandler", new LogHandler().getHandlerName());
    }
}