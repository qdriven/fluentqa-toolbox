package io.qmeta.wps.yunfile.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateFolderRequest {
    private String appid;
    private String access_token;
    private String open_parentid;
    private String name;
}
