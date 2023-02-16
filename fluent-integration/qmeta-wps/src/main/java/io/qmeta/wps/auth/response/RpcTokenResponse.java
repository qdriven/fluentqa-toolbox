package io.qmeta.wps.auth.response;

import lombok.Data;

@Data
public class RpcTokenResponse {
    private String result;
    private String rpc_token;
}
