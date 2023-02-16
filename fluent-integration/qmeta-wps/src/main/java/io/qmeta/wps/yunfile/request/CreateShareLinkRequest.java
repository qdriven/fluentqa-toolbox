package io.qmeta.wps.yunfile.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateShareLinkRequest {
    private String appid;
    private String access_token;
    //权限：write/read,默认read
    private String open_fileid;
    //有效时长：0(永久) / 604800(7天) / 2592000(30天)，默认-1
    private String permission;
    //有效时长：0(永久) / 604800(7天) / 2592000(30天)，默认-1
    private long period=2592000;
}
