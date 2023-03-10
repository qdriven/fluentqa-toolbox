package io.fluentqa.qabox.bytedance.open.api.v1.request.code;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 提交代码
 * @Authorn
 * @date 2020/07/01
 **/
@Data
public class CodeUploadRequest implements IByteDanceRequest {

    /**
     * 模版 ID
     */
    @JSONField(name = "template_id")
    @JsonAlias("template_id")
    @JsonProperty("template_id")
    @SerializedName("template_id")
    private Long templateId;
    /**
     * 提交描述
     */
    @JSONField(name = "user_desc")
    @JsonAlias("user_desc")
    @JsonProperty("user_desc")
    @SerializedName("user_desc")
    private String userDesc;
    /**
     * 提交版本
     */
    @JSONField(name = "user_version")
    @JsonAlias("user_version")
    @JsonProperty("user_version")
    @SerializedName("user_version")
    private String userVersion;
    /**
     * ext_json 配置信息，必须是 JSON String
     */
    @JSONField(name = "ext_json")
    @JsonAlias("ext_json")
    @JsonProperty("ext_json")
    @SerializedName("ext_json")
    private String extJson;

}
