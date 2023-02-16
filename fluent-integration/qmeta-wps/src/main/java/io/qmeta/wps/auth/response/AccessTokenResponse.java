package io.qmeta.wps.auth.response;

import lombok.Data;


@Data
public class AccessTokenResponse {
    private String appid;
    private long expires_in;
    private String access_token;
    private String refresh_token;
    private String openid;
}
