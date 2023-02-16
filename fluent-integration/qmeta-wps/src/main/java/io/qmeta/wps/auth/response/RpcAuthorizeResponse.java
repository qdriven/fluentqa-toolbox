package io.qmeta.wps.auth.response;

import lombok.Data;

@Data
public class RpcAuthorizeResponse {
    private int result;
    private int authorized;
}
