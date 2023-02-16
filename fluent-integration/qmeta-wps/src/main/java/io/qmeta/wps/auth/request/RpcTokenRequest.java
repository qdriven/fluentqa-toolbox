package io.qmeta.wps.auth.request;

import lombok.Data;

@Data
public class RpcTokenRequest {

    private String access_token;
    private String appid;
    private String scope;
}
