package io.fluentqa.qabox.bytedance.open.api.v1.response.material;

import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/07/20
 **/
@Data
public class UploadPicMaterialResponse extends ByteDanceError implements IByteDanceResponse {
    private String data;
}
