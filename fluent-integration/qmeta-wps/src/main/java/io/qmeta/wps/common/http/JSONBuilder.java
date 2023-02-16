package io.qmeta.wps.common.http;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONBuilder {

    private JSONObject jsonObject = new JSONObject();

    public JSONBuilder put(String key, Object value) {
        try {
            jsonObject.put(key, value);
            return this;
        } catch (JSONException e) {
            throw new IllegalArgumentException("Bad param, key=" + key + " value=" + value);
        }
    }

    public String build() {
        return jsonObject.toString();
    }
}
