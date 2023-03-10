package io.fluentqa.qabox.bytedance.open.api.v1.request.auth;

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
public class GetComponentAccessTokenRequest implements IByteDanceRequest {

    @JSONField(name = "component_appid")
    @JsonAlias("component_appid")
    @JsonProperty("component_appid")
    @SerializedName("component_appid")
    private String componentAppid;

    @JSONField(name = "component_appsecret")
    @JsonAlias("component_appsecret")
    @JsonProperty("component_appsecret")
    @SerializedName("component_appsecret")
    private String componentAppsecret;

    @JSONField(name = "component_ticket")
    @JsonAlias("component_ticket")
    @JsonProperty("component_ticket")
    @SerializedName("component_ticket")
    private String componentTicket;
}
