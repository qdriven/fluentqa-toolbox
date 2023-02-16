package io.qmeta.wps.common.http;

import io.qmeta.wps.yunfile.exception.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.zip.GZIPInputStream;

public final class Response {
    private okhttp3.Response response;

    private String cacheBodyString;

    Response(okhttp3.Response response) {
        this.response = response;
    }

    public boolean isSuccessful() {
        return response.isSuccessful();
    }

    public String getHeader(String header) {
        return response.header(header);
    }

    public int code() {
        return response.code();
    }

    public void close() {
        response.close();
    }

    public String bodyString() throws IOException {
        if (cacheBodyString == null) {
            if ("gzip".equals(response.header("Content-Encoding"))) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new GZIPInputStream(byteStream()), "utf-8"));
                String line;
                cacheBodyString = "";
                try {
                    while ((line = reader.readLine()) != null)
                        cacheBodyString += line;
                } finally {
                    reader.close();
                }
            } else {
                cacheBodyString = response.body().string();
            }
        }
        return cacheBodyString;
    }

    public InputStream byteStream() throws IOException {
        return response.body().byteStream();
    }

    public long contentLength() {
        return response.body().contentLength();
    }

    public String contentType() throws IOException {
        return response.body().contentType().toString();
    }

//    public void copyFile(File destFile, ProgressListener listener) throws IOException, YunException {
//        InputStream input = byteStream();
//        long fileSize = destFile.length();
//        long top = 0;
//        long size = contentLength(); // input.available(); available拿到的流长度不准确
//        if (listener != null && size > 0)
//            listener.onProgress(fileSize, size + fileSize);
//
//        FileOutputStream output = new FileOutputStream(destFile, true);
//        byte[] bytes = new byte[4 * 1024];
//        int len;
//        try {
//            while ((len = input.read(bytes)) > 0) {
//                output.write(bytes, 0, len);
//                // progress
//                top += len;
//                if (listener != null && top < size) {
//                    if (!listener.onProgress(fileSize + top, fileSize + size)) { // 返回false表示中断下载
//                        throw new YunCancelException("download request is canceled.");
//                    }
//                }
//            }
//
//            if (listener != null && size > 0)
//                listener.onProgress(fileSize + size, fileSize + size);
//            // 网络流有可能返回大小为0
//            if (listener != null && size <= 0 && top > 0) {
//                listener.onProgress(fileSize, top + fileSize);
//                listener.onProgress(fileSize + top, fileSize + top);
//            }
//        } finally {
//            if (null != output) {
//                try {
//                    output.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//    }

    public JSONObject getJSONObject() throws YunException {
        parseXML(this);
        return parseJSON(this);
    }

//    public void writeToFile(File destFile, ProgressListener listener) throws YunException, IOException {
//        parseXML(this);
//        this.copyFile(destFile, listener);
//    }

    public static void parseIOException(IOException e) throws YunException {
        throw analyzeIOException(e);
    }

    public static YunException analyzeIOException(IOException e) {
        if (e.getCause() instanceof YunCancelException)
            return new YunCancelException("upload request is cancelled.");
        return new YunHttpIOException(e);
    }

    private static JSONObject parseJSON(Response response) throws YunException {
        String jsonString = null;
        try {
            jsonString = response.bodyString();
        } catch (IOException e) {
            parseIOException(e);
        }

        JSONObject jsonObject = null;
        try {
            if (jsonString == null || jsonString.length() == 0)
                jsonString = "{}";
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            throw new YunJsonException(jsonString, response.code(), e);
        }

        /*
         * 云API 默认均带"result"
         * KS3 不带"result"
         */
        //securityDocApi可能会返加这个结果{"code":400003,"msg":"no read permission."}
        if (!response.isSuccessful()) {
            if (jsonObject.has("result")) {
                String result = jsonObject.optString("result");
                if (!"ok".equals(result.toLowerCase()) && !"0".equals(result)) {
                    throw new YunResultException(result, jsonObject.optString("msg"), response.code(), jsonObject);
                }
            } else if (jsonObject.has("code") && jsonObject.has("msg")) {
                //目前securityDocapi的服务器返回比较特殊的信息
                //做为兼容，这里进行异常处理
                throw new YunCodeException(jsonString, response.code());
            }
        }
        return jsonObject;
    }

    /**
     * 访问KS3 出错，返回的文本是XML格式，错误码格式 <Code>SignatureDoesNotMatch</Code>
     */
    private static void parseXML(Response response) throws YunException {
        if (response.isSuccessful())
            return;
        String xmlString = null;
        try {
            xmlString = response.bodyString();
        } catch (IOException e) {
            parseIOException(e);
        }

        if (xmlString.startsWith("<?xml")) {
            int fromIndex = xmlString.indexOf("<Code>");
            if (fromIndex != -1) {
                fromIndex += 6;
                int toIndex = xmlString.indexOf("</Code>", fromIndex);
                if (toIndex != -1) {
                    throw new YunXmlException(xmlString.substring(fromIndex, toIndex), xmlString, response.code());
                }
            }
            throw new YunXmlException(null, xmlString, response.code());
        }
        if (xmlString.startsWith("<html>")) {
            throw new YunHtmlException(xmlString, response.code());
        }
    }
}
