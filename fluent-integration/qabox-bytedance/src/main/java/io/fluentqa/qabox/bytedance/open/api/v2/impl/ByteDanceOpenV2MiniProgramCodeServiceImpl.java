package io.fluentqa.qabox.bytedance.open.api.v2.impl;

import io.fluentqa.qabox.bytedance.open.api.v2.IByteDanceOpenV2MiniProgramCodeService;
import io.fluentqa.qabox.bytedance.open.api.v2.IByteDanceOpenV2MiniProgramService;
import io.fluentqa.qabox.bytedance.open.api.v2.request.code.CodeAuditRequest;
import io.fluentqa.qabox.bytedance.open.api.v2.response.code.CodeAuditResponse;

/**
 * @Authorn
 * @date 2020/09/18
 **/
public class ByteDanceOpenV2MiniProgramCodeServiceImpl implements IByteDanceOpenV2MiniProgramCodeService {

    private IByteDanceOpenV2MiniProgramService byteDanceOpenV2MiniProgramService;

    public ByteDanceOpenV2MiniProgramCodeServiceImpl(IByteDanceOpenV2MiniProgramService byteDanceOpenV2MiniProgramService) {
        this.byteDanceOpenV2MiniProgramService = byteDanceOpenV2MiniProgramService;
    }

    @Override
    public CodeAuditResponse audit(CodeAuditRequest request) {
        return byteDanceOpenV2MiniProgramService.post(AUDIT_URL, request, CodeAuditResponse.class);
    }
}
