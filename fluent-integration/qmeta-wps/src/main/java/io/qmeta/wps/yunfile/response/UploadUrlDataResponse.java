package io.qmeta.wps.yunfile.response;

import lombok.Data;

import java.beans.Transient;
import java.util.Map;

@Data
public class UploadUrlDataResponse {
    private int result;
    private Data data;

    @Transient
    public String getUploadUrl(){
        return this.data.getUploadinfo().put_auth.get("upload_url");
    }

    public Map<String,Object> getHeaders(){
        return this.data.getUploadinfo().getHeaders();
    }

    @lombok.Data
    public static class Data {
        private UploadInfoResp uploadinfo;
    }

    @lombok.Data
    public static class UploadInfoResp {
        private Map<String, Object> headers;
        private Map<String,String> put_auth;
    }
}
