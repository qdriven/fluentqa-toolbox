package io.qmeta.wps.auth;

import cn.hutool.core.util.StrUtil;

/**
 * scope	说明
 * user_info	获取用户信息
 * vas	增值服务(支付相关)
 * cloud_file	云文档操作
 * file_selector	文件选择器（申请获取WPS云文档权限时填写
 */
public enum WpsOpenAPIScope {
//    scope,
    user_info,cloud_file,file_selector;

    public static String allScopes(){

        return StrUtil.join(",",WpsOpenAPIScope.values());
    }
}
