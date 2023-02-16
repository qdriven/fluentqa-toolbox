package io.fluentqa.qabox.bytedance.open.api.v1.response.code;

import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/07/01
 **/
@Data
public class CodeAuditResponse extends ByteDanceError implements IByteDanceResponse {

    private static final long serialVersionUID = -5789908116611917739L;
}
