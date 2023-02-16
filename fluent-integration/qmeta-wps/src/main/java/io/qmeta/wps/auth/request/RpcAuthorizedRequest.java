package io.qmeta.wps.auth.request;

import lombok.Data;

@Data
public class RpcAuthorizedRequest {

    private String rpc_token;
    private String appid;
    private String scope;
}
