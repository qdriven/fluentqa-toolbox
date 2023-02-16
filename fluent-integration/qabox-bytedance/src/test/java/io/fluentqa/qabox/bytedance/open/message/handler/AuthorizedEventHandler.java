package io.fluentqa.qabox.bytedance.open.message.handler;

import io.fluentqa.qabox.bytedance.open.message.ByteDanceOpenMessage;
import io.fluentqa.qabox.bytedance.open.message.ByteDanceOpenMessageHandleResult;
import io.fluentqa.qabox.bytedance.open.message.IByteDanceOpenMessageHandler;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 授权事件处理器
 * @Authorn
 * @date 2020/07/10
 **/
@Slf4j
public class AuthorizedEventHandler implements IByteDanceOpenMessageHandler {

    @Override
    public ByteDanceOpenMessageHandleResult handle(ByteDanceOpenMessage message, Map<String, Object> context) {
        log.info("AuthorizedEventHandler handle message");
        return null;
    }
}
