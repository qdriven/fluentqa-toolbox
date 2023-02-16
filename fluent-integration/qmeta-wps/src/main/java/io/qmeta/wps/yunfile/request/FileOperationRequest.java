package io.qmeta.wps.yunfile.request;

import lombok.Data;

@Data
public class FileOperationRequest {
    private String appid;
    private String access_token;
    //文件id列表，用,分隔
    private String open_fileids;
    private String open_from_parentid;
    private String open_to_parentid;
    //for search file
    private String open_parentid;
    private String offset;
    private String count;
    //for serach file by content
    private String content;
}
