package io.fluentqa.qabox.bytedance.open.api.v1.request.template;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 删除模版
 * @Authorn
 * @date 2020/07/01
 **/
@Data
public class TemplateDelTplRequest implements IByteDanceRequest {

    /**
     * 模版 ID
     */
    @JSONField(name = "template_id")
    @JsonAlias("template_id")
    @JsonProperty("template_id")
    @SerializedName("template_id")
    private Integer templateId;
}
