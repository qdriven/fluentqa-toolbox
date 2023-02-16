package io.qmeta.wps.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
/**
 * ToDo: 可能存在不同的Key，使用Map保存
 */
public class WpsApiKeyHolder {
    @Value("${wps.wxWork_appid: defaultValue}")
    private String appId;
    @Value("${wps.wxWork_appKey:defaultValue}")
    private String appKey;
    @Value("${wps.openApiServer:https://openapi.wps.cn}")
    private String openApiServer = "https://openapi.wps.cn";
    @Value("${wps.code}")
    private String code;
    private String accessToken = "1134c0e5c6f40ef7ee2bde1e153b7d98";

    public String getAppId() {
        return this.appId;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public String getAccessToken() {
        //TODO: doing refresh
        return accessToken;
    }
}
