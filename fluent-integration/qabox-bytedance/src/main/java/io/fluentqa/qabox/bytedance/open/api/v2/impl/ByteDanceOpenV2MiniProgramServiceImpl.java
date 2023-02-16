package io.fluentqa.qabox.bytedance.open.api.v2.impl;

import io.fluentqa.qabox.bytedance.open.api.impl.AbstractByteDanceOpenMiniProgramService;
import io.fluentqa.qabox.bytedance.open.api.IByteDanceOpenConfigStorage;
import io.fluentqa.qabox.bytedance.open.api.IByteDanceOpenService;
import io.fluentqa.qabox.bytedance.open.api.v2.IByteDanceOpenV2ComponentService;
import io.fluentqa.qabox.bytedance.open.api.v2.IByteDanceOpenV2MiniProgramCodeService;
import io.fluentqa.qabox.bytedance.open.api.v2.IByteDanceOpenV2MiniProgramService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * @Authorn
 * @date 2020/09/21
 **/
@Slf4j
public class ByteDanceOpenV2MiniProgramServiceImpl extends AbstractByteDanceOpenMiniProgramService implements IByteDanceOpenV2MiniProgramService {

    private IByteDanceOpenV2ComponentService byteDanceOpenV2ComponentService;

    private IByteDanceOpenV2MiniProgramCodeService byteDanceOpenV2MiniProgramCodeService;


    public ByteDanceOpenV2MiniProgramServiceImpl(IByteDanceOpenV2ComponentService byteDanceOpenV2ComponentService, String appId) {
        super(appId);
        this.byteDanceOpenV2ComponentService = byteDanceOpenV2ComponentService;
        this.byteDanceOpenV2MiniProgramCodeService = new ByteDanceOpenV2MiniProgramCodeServiceImpl(this);
    }

    @Override
    public IByteDanceOpenV2MiniProgramCodeService getByteDanceOpenV2MiniProgramCodeService() {
        return byteDanceOpenV2MiniProgramCodeService;
    }

    @Override
    public IByteDanceOpenService getByteDanceOpenService() {
        return byteDanceOpenV2ComponentService.getByteDanceOpenService();
    }

    @Override
    public IByteDanceOpenConfigStorage getByteDanceOpenConfigStorage() {
        return byteDanceOpenV2ComponentService.getByteDanceOpenService().getByteDanceOpenConfigStorage();
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    public String getAccessToken() {
        return getAccessToken(false);
    }

    @Override
    public String getAccessToken(boolean forceRefresh) {
        return byteDanceOpenV2ComponentService.getAuthorizerAccessToken(getAppId(), forceRefresh);
    }
}
