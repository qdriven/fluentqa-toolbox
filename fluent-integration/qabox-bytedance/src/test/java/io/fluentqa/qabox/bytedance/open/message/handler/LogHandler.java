package io.fluentqa.qabox.bytedance.open.message.handler;

import io.fluentqa.qabox.bytedance.open.message.ByteDanceOpenMessage;
import io.fluentqa.qabox.bytedance.open.message.ByteDanceOpenMessageHandleResult;
import io.fluentqa.qabox.bytedance.open.message.IByteDanceOpenMessageHandler;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @Authorn
 * @date 2020/07/08
 **/
@Slf4j
public class LogHandler implements IByteDanceOpenMessageHandler {

    @Override
    public ByteDanceOpenMessageHandleResult handle(ByteDanceOpenMessage message, Map<String, Object> context) {
        log.info("LogHandler handle message");
        return null;
    }

    @Override
    public boolean repeatable() {
        return true;
    }
}
