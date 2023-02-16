package io.qmeta.wps.yunfile.response;

import lombok.Data;

@Data
public class CreateCommitFileResponse {
    private int result;
    private CommitFileInfo data;

    @Data
    public static class CommitFileInfo{
        private String file_name;
        private String open_fileid;
    }
}
