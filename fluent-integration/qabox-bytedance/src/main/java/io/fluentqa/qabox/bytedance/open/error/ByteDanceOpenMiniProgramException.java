package io.fluentqa.qabox.bytedance.open.error;

import io.fluentqa.qabox.bytedance.common.error.ByteDanceErrorException;
import io.fluentqa.qabox.bytedance.common.error.IByteDanceError;

/**
 * @Authorn
 * @date 2020/07/29
 **/
public class ByteDanceOpenMiniProgramException extends ByteDanceErrorException {

    private String appid;

    public ByteDanceOpenMiniProgramException(String appid, IByteDanceError error) {
        super(error);
        this.appid = appid;
    }

    public ByteDanceOpenMiniProgramException(String appid, IByteDanceError error, Throwable cause) {
        super(error, cause);
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }
}
