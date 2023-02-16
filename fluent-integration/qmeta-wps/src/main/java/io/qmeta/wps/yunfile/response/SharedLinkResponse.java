package io.qmeta.wps.yunfile.response;

import lombok.Data;

@Data
public class SharedLinkResponse {
    private String file_name;
    private String link_url;
    private int result;
}
