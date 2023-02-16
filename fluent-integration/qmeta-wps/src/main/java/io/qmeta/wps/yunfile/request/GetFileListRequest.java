package io.qmeta.wps.yunfile.request;

import lombok.Data;

@Data
public class GetFileListRequest {
    private String appid;
    private String access_token;
    private String open_parentid;
    private String order;
    private String order_by;
    private int offset;
    private int count;
}
