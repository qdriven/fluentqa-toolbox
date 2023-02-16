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
public class AppModifyAppIntroRequest implements IByteDanceRequest {

    /**
     * 授权小程序准备修改的简介
     */
    @JSONField(name = "new_intro")
    @JsonAlias("new_intro")
    @JsonProperty("new_intro")
    @SerializedName("new_intro")
    private String newIntro;

}
