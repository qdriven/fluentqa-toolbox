package io.qmeta.wps.yunfile.response;

import lombok.Data;

@Data
public class CreateFolderResponse {
    private FolderInfoResp data;
    private long result;

    @Data
    public static class FolderInfoResp {
        private String file_name;
        private String open_folderid;
    }
}