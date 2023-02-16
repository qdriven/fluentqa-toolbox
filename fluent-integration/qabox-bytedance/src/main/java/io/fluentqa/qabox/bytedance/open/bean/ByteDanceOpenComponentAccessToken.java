package io.fluentqa.qabox.bytedance.open.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import io.fluentqa.qabox.bytedance.common.util.json.ByteDanceJsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/06/28
 **/
@Data
public class ByteDanceOpenComponentAccessToken extends ByteDanceError implements IByteDanceResponse, Serializable {

    private static final long serialVersionUID = 6417155856835777760L;

    @JsonProperty("component_access_token")
    @JSONField(name = "component_access_token")
    @SerializedName("component_access_token")
    private String componentAccessToken;

    @JsonProperty("expires_in")
    @JSONField(name = "expires_in")
    @SerializedName("expires_in")
    private int expiresIn = -1;

    public static ByteDanceOpenComponentAccessToken fromJson(String json) {
        return ByteDanceJsonBuilder.instance().parse(json, ByteDanceOpenComponentAccessToken.class);
    }
}
