package io.fluentqa.qabox.bytedance.open.api.v2.request.code;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceRequest;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/07/01
 **/
@Data
public class CodeAuditRequest implements IByteDanceRequest {
    @JSONField(name = "hostNames")
    @JsonAlias("hostNames")
    @JsonProperty("hostNames")
    @SerializedName("hostNames")
    private List<String> hostNames;
}
