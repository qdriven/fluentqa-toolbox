package io.fluentqa.qabox.bytedance.open.api.v1.request.material;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceRequest;
import com.google.gson.annotations.SerializedName;
import java.io.File;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/07/20
 **/
@Data
public class UploadPicMaterialRequest implements IByteDanceRequest {
    /**
     * 上传的文件类型:
     * 1: 小程序图标材料
     * 2: 服务类目资质材料
     * 3: 小程序名称审核资料
     */
    @JSONField(name = "material_type")
    @JsonAlias("material_type")
    @JsonProperty("material_type")
    @SerializedName("material_type")
    private String materialType;

    @JSONField(name = "material_file")
    @JsonAlias("material_file")
    @JsonProperty("material_file")
    @SerializedName("material_file")
    private File materialFile;

}
