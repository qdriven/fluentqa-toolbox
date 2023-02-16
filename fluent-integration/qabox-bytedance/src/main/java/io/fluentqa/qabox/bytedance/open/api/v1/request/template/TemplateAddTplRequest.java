package io.fluentqa.qabox.bytedance.open.api.v1.request.template;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 将草稿设置为模版
 * @Authorn
 * @date 2020/07/01
 **/
@Data
public class TemplateAddTplRequest implements IByteDanceRequest {

    /**
     * 草稿 ID
     */
    @JSONField(name = "draft_id")
    @JsonAlias("draft_id")
    @JsonProperty("draft_id")
    @SerializedName("draft_id")
    private Integer draftId;
}
