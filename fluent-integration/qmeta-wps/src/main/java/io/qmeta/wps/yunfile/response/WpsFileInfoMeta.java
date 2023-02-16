package io.qmeta.wps.yunfile.response;

import lombok.Data;

@Data
public class WpsFileInfoMeta {
    private String open_fileid;
    private String file_name;
    private String file_type;
    private long ctime;
    private long mtime;
}