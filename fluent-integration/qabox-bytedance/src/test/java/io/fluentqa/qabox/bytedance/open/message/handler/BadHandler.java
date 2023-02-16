package io.fluentqa.qabox.bytedance.open.message.handler;

import io.fluentqa.qabox.bytedance.open.message.ByteDanceOpenMessage;
import io.fluentqa.qabox.bytedance.open.message.ByteDanceOpenMessageHandleResult;
import io.fluentqa.qabox.bytedance.open.message.IByteDanceOpenMessageHandler;

import java.util.Map;

/**
 * @Authorn
 * @date 2020/08/17
 **/
public class BadHandler implements IByteDanceOpenMessageHandler {

    @Override
    public ByteDanceOpenMessageHandleResult handle(ByteDanceOpenMessage message, Map<String, Object> context) {
        throw new RuntimeException("handler 处理 message异常");
    }
}
