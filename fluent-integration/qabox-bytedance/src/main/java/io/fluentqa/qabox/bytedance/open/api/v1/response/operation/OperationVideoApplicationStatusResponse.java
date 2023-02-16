package io.fluentqa.qabox.bytedance.open.api.v1.response.operation;

import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import lombok.Data;

@Data
public class OperationVideoApplicationStatusResponse extends ByteDanceError implements IByteDanceResponse {

    private static final long serialVersionUID = 5914133377456005753L;

    private DataObj data;

    @Data
    public static class DataObj{
        /**
         * 「短视频挂载」能力申请状态。
         * 1：申请中，2：申请通过，3：申请失败，4：能力关闭，5：可申请，6：不可申请
         */
        private Integer status;

        /**
         * 「短视频挂载」能力申请状态对应的原因
         * 如果状态是1（申请中）、2（申请通过）、5（可申请），则 reason 为空字符串
         */
        private String reason;
    }


}