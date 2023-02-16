package io.fluentqa.qabox.bytedance.open.api.v1.request.appinfo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/06/28
 **/
@Data
public class AppInfoRequest implements IByteDanceRequest {
    @JSONField(name = "component_appid")
    @JsonAlias("component_appid")
    @JsonProperty("component_appid")
    @SerializedName("componentAppid")
    private String componentAppid;

    @JSONField(name = "authorizer_access_token")
    @JsonAlias("authorizer_access_token")
    @JsonProperty("authorizer_access_token")
    @SerializedName("authorizer_access_token")
    private String authorizerAccessToken;
}
