package io.fluentqa.qabox.bytedance.open.api.v1.request.code;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @Authorn
 * @date 2021/03/12
 **/
@Data
public class CensorImageRequest implements IByteDanceRequest {

    @SerializedName("app_id")
    @JSONField(name = "app_id")
    @JsonAlias("app_id")
    @JsonProperty("app_id")
    private String appId;

    @SerializedName("access_token")
    @JSONField(name = "access_token")
    @JsonAlias("access_token")
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 检测的图片链接
     */
    private String image;

    /**
     * 图片数据的 base64 格式，有 image 字段时，此字段无效
     */
    @SerializedName("image_data")
    @JSONField(name = "image_data")
    @JsonAlias("image_data")
    @JsonProperty("image_data")
    private String imageData;
}
