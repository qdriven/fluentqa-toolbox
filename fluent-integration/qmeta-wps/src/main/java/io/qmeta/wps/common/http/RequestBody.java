package io.qmeta.wps.common.http;

import okhttp3.MediaType;

import java.io.*;

public final class RequestBody {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private JSONBuilder jsonBuilder;

    private MediaType mediaType;
    private File file;
    private byte[] content;
    private String bodyString;

    private ProgressListener listener;

    public RequestBody() {
        this.mediaType = JSON;
    }

    public RequestBody(JSONBuilder jsonBuilder) {
        this.mediaType = JSON;
        this.jsonBuilder = jsonBuilder;
    }

    public RequestBody(File file) {
        this.file = file;
    }

    public RequestBody(String mediaType, File file, ProgressListener listener) {
        this.mediaType =  MediaType.parse(mediaType);
        this.file = file;
        this.listener = listener;
    }

    public RequestBody(String mediaType, byte[] content) {
        this.mediaType =  MediaType.parse(mediaType);
        this.content = content;
    }

    public RequestBody(String mediaType, String content) {
        this.mediaType =  MediaType.parse(mediaType);
        this.bodyString = content;
    }

    public okhttp3.RequestBody create() {
        if (jsonBuilder != null)
            return okhttp3.RequestBody.create(JSON, jsonBuilder.build());

        if (file != null)
            return okhttp3.RequestBody.create(mediaType, file);
        if (content != null)
            return okhttp3.RequestBody.create(mediaType, content);
        if (bodyString != null)
            return okhttp3.RequestBody.create(mediaType, bodyString);

        return okhttp3.RequestBody.create(JSON, "{}");
    }

    public String contentType() {
        return mediaType.toString();
    }

    public InputStream content() {
        InputStream inputStream = null;
        if (this.file != null) {
            try {
                inputStream = new FileInputStream(this.file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else if (null != jsonBuilder) {
            try {
                byte[] json = jsonBuilder.build().getBytes("utf-8");
                inputStream = new ByteArrayInputStream(json);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (content != null) {
            return new ByteArrayInputStream(content);
        } else if (bodyString != null) {
            try {
                return new ByteArrayInputStream(bodyString.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }

}
