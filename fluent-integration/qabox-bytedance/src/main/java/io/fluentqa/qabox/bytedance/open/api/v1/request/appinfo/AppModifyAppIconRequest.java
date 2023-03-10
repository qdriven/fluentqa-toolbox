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
public class AppModifyAppIconRequest implements IByteDanceRequest {

    /**
     * 授权小程序准备修改的图标路径
     * 注意：需要使用 上传图片材料 接口返回的路径才可以，否则报错。
     */
    @JSONField(name = "new_icon_path")
    @JsonAlias("new_icon_path")
    @JsonProperty("new_icon_path")
    @SerializedName("new_icon_path")
    private String newIconPath;

}
