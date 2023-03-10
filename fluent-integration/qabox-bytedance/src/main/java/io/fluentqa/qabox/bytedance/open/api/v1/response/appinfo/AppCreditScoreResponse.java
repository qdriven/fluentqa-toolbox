package io.fluentqa.qabox.bytedance.open.api.v1.response.appinfo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/11/23
 **/
@Data
public class AppCreditScoreResponse extends ByteDanceError implements IByteDanceResponse {

    private static final long serialVersionUID = -1473053594294676243L;

    private DataObj data;

    @Data
    public static class DataObj{

        /**
         * 信用分分值
         */
        @JSONField(name = "creditScore")
        @JsonAlias("creditScore")
        @JsonProperty("creditScore")
        @SerializedName("creditScore")
        private Integer creditScore;
    }
}
