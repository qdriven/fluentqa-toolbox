package io.qmeta.wps.yunfile.exception;

import org.json.JSONObject;

public class YunResultException extends YunException {

    private String result;
    private int code;
    private JSONObject jsonObject;

    public YunResultException(String result, String msg) {
        super(msg);
        this.result = result;
    }

    public YunResultException(String result, String msg, int code, JSONObject jsonObject) {
        super(msg);
        this.result = result;
        this.code = code;
        this.jsonObject = jsonObject;
    }

    public YunResultException(String result, String msg, Exception e) {
        super(msg, e);
        this.result = result;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public int getCode() {
        return code;
    }

    public JSONObject getJSONObject() {
        return jsonObject;
    }
}
