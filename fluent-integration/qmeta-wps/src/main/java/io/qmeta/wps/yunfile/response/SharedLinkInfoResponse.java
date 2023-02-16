package io.qmeta.wps.yunfile.response;


import lombok.Data;

@Data
public class SharedLinkInfoResponse {
    private int result;
    private SharedLinkInfoMeta link_info;

    @Data
    public static class SharedLinkInfoMeta {
        private String file_name;
        private String file_type;
        private String file_size;
        private String link_permission;
        private String status;
        private String expire_time;
    }
}
