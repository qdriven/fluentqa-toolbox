package io.qmeta.wps.yunfile.response;

import lombok.Data;

import java.util.List;

@Data
public class FileListResponse {
    private int result;
    private List<WpsFileInfoMeta> files;
}
