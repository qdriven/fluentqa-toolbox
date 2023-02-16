package io.qmeta.wps.yunfile.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileRequest {
    private String appid;
    private String access_token;
    //文件id列表，用,分隔
    private String open_fileid;
}
