package io.qmeta.wps.yunfile.response;

import lombok.Data;

@Data
public class FileInfoResponse {
    private int result;
    private WpsFileInfoMeta file_info;
}
