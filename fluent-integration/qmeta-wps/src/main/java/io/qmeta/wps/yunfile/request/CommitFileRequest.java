package io.qmeta.wps.yunfile.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommitFileRequest {
    private String appid;
    private String access_token;
    private String open_parentid;
    private long size;
    private String sha1;
    private String name;
    private String etag;
    private boolean add_name_index;
    private String open_fileid;
}
