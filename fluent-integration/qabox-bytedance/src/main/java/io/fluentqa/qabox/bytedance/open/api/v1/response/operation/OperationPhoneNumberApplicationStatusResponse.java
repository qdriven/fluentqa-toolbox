package io.fluentqa.qabox.bytedance.open.api.v1.response.operation;

import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import lombok.Data;

@Data
public class OperationPhoneNumberApplicationStatusResponse extends ByteDanceError implements IByteDanceResponse {

    private static final long serialVersionUID = 4647357879725854623L;

    private DataObj data;

    @Data
    public static class DataObj{
        /**
         * 「获取用户手机号」能力申请状态。
         * 0：默认值，1：可申请，2：不可申请，3：申请中，4：申请通过，5：申请失败，6：能力关闭
         */
        private Integer status;

        /**
         * 「获取用户手机号」能力申请状态对应的原因
         * 如果状态是0（默认值）、1（可申请）、3（申请中）、4（申请通过），则 reason 为空字符串
         */
        private String reason;
    }


}