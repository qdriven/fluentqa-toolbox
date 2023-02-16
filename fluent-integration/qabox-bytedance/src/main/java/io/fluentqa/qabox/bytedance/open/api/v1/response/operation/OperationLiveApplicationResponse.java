package io.fluentqa.qabox.bytedance.open.api.v1.response.operation;

import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import lombok.Data;

@Data
public class OperationLiveApplicationResponse extends ByteDanceError implements IByteDanceResponse {

    private static final long serialVersionUID = -2262561752483791074L;
}