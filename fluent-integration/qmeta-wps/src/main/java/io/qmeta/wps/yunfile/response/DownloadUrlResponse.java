package io.qmeta.wps.yunfile.response;

import lombok.Data;

@Data
public class DownloadUrlResponse {
    private int result;
    private String url;
}
