package io.fluentqa.qabox.bytedance.open.api.v1.response.auth;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/12/22
 **/
@Data
public class AuthAppListResponse extends ByteDanceError implements IByteDanceResponse {

    private DataObj data;

    @Data
    public static class DataObj{
        @SerializedName("authAppList")
        @JSONField(name = "authAppList")
        @JsonAlias("authAppList")
        @JsonProperty("authAppList")
        private List<AuthApp> authAppList;

        @SerializedName("total")
        @JSONField(name = "total")
        @JsonAlias("total")
        @JsonProperty("total")
        private Integer total;
    }

    @Data
    public static class AuthApp{

        @SerializedName("authAppId")
        @JSONField(name = "authAppId")
        @JsonAlias("authAppId")
        @JsonProperty("authAppId")
        private String authAppId;

        @SerializedName("authTime")
        @JSONField(name = "authTime")
        @JsonAlias("authTime")
        @JsonProperty("authTime")
        private Long authTime;

    }
}
