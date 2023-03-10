package io.fluentqa.qabox.bytedance.open.api.v1.response.auth;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/06/28
 **/
@Data
public class GetPreAuthCodeResponse extends ByteDanceError implements IByteDanceResponse {

    private static final long serialVersionUID = -1208735625532808579L;

    @SerializedName("pre_auth_code")
    @JSONField(name = "pre_auth_code")
    @JsonAlias("pre_auth_code")
    @JsonProperty("pre_auth_code")
    private String preAuthCode;

    @SerializedName("expires_in")
    @JSONField(name = "expires_in")
    @JsonAlias("expires_in")
    @JsonProperty("expires_in")
    private String expiresIn;

}
