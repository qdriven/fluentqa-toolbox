package io.qmeta.wps.auth.response;

import lombok.Data;

import java.beans.Transient;

@Data
public class GetAccessTokenResponse {
    private int result;
    private AccessTokenResponse token;

    @Transient
    public String getAccessToken() {
        return token.getAccess_token();
    }
}
