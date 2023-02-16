package io.fluentqa.qabox.bytedance.open.api.v1.request.appinfo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/07/20
 **/
@Data
public class AppCheckAppNameRequest implements IByteDanceRequest {
    @JSONField(name = "app_name")
    @JsonAlias("app_name")
    @JsonProperty("app_name")
    @SerializedName("app_name")
    private String appName;
}
