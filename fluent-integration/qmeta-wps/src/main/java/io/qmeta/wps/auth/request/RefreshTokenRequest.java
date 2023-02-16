package io.qmeta.wps.auth.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String appid;
    private String appkey;
    private String refresh_token;
}
