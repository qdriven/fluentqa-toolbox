package io.fluentqa.qabox.bytedance.open.api.v2;

import io.fluentqa.qabox.bytedance.open.api.v2.request.code.CodeAuditRequest;
import io.fluentqa.qabox.bytedance.open.api.v2.response.code.CodeAuditResponse;

/**
 * @Authorn
 * @date 2020/09/18
 **/
public interface IByteDanceOpenV2MiniProgramCodeService {

    String AUDIT_URL = "https://open.microapp.bytedance.com/openapi/v2/microapp/package/audit";


    CodeAuditResponse audit(CodeAuditRequest request);

}
