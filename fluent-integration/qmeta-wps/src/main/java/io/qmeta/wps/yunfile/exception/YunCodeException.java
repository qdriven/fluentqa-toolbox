package io.qmeta.wps.yunfile.exception;

/**
 * Created by jimtoner on 30/06/2017.
 * 在碰到!response.isSuccessful() 时jsonObject中如有：
 * securityDocApi可能会返加这个结果{"code":400003,"msg":"no read permission."}
 * 时会抛此异常
 */
public class YunCodeException extends YunException {
    private int code;

    public YunCodeException(String jsonstring, int code) {
        super(jsonstring);
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
