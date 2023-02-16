package io.qmeta.wps.yunfile.request;

import lombok.Data;

@Data
public class CreateFileRequest {
    private String appid;
    private String access_token;
    //父目录，默认为"0"
    private String open_parentid="0";
    private String file_name;
    //false:重名创建失败 true:重名时添加数字后缀
    private boolean add_name_index;
}
