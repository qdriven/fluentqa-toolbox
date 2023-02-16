package io.qmeta.wps.yunfile.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadUrlDataRequest {

    private String access_token;
    private String appid;
    private String open_parentid;
    private long size;
    private String name;
    private String sha1;
}
