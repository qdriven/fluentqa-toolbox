package io.qmeta.wps.yunfile.exception;

import org.json.JSONException;

public class YunJsonException extends YunException {

    private int code;

    public YunJsonException(String jsonstring, JSONException e) {
        super(jsonstring, e);
    }

    public YunJsonException(String jsonstring, int code, JSONException e) {
        super(jsonstring, e);
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
