package io.fluentqa.qabox.bytedance.open.api.v1.response.material;

import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import lombok.Data;

@Data
public class UploadMaterialResponse extends ByteDanceError implements IByteDanceResponse {

    private UploadMaterialResponseData data;

    @Data
    public static class UploadMaterialResponseData{
        private String path;
    }
}
