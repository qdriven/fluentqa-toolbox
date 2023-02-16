package io.qmeta.wps.common.http;

import cn.hutool.core.bean.BeanUtil;
import io.qmeta.wps.yunfile.exception.YunHttpIOException;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;


/**
 * cn.wps.http 是对okhttp或HttpClient 的简单封装，目的是让上层和网络层解耦
 * <p>
 * 同时提供一批工具类，比如：
 * <p>
 * 1.JSON 构造器
 * 2.URL 构造器
 */
public final class OkHttpClientWrapper {
    private static OkHttpClient client = createClient();
    private okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
    private String requestUrl;

    private static void resetClient() {
        client.connectionPool().evictAll();
        client = createClient();
    }

    private static OkHttpClient createClient() {
        //防止SSLPeerUnverifiedException问题，加verifier
        return new OkHttpClient.Builder()
                .hostnameVerifier((hostname, session) -> true).build();
    }

    public OkHttpClientWrapper addQueryParameters(Map<String, Object> queryParameters) {
        this.requestUrl = this.requestUrl + "?";
        for (Map.Entry<String, Object> entry : queryParameters.entrySet()) {
            this.requestUrl = this.requestUrl + entry.getKey() + "=" + entry.getValue().toString() + "&";
        }
        builder.url(this.requestUrl);
        return this;
    }

    public OkHttpClientWrapper get(Object queryBean) {
        Map<String, Object> queryParameters = BeanUtil.beanToMap(queryBean);
        return addQueryParameters(queryParameters);
    }

    public OkHttpClientWrapper url(String url) {
        this.requestUrl = url;
        builder.url(this.requestUrl);
        return this;
    }

    public OkHttpClientWrapper addHeader(String name, String value) {
        builder.addHeader(name, value);
        return this;
    }

    public OkHttpClientWrapper addHeaders(Map<String, Object> headers) {
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue().toString());
        }
        return this;
    }

    public OkHttpClientWrapper addHeader(String name, boolean value) {
        builder.addHeader(name, Boolean.toString(value));
        return this;
    }

    public OkHttpClientWrapper get() {
        builder.get();
        return this;
    }

    public OkHttpClientWrapper post(RequestBody body) {
        builder.post(body == null ? new RequestBody().create() : body.create());
        return this;
    }

    public OkHttpClientWrapper put(RequestBody body) {
        builder.put(body == null ? new RequestBody().create() : body.create());
        return this;
    }

    public OkHttpClientWrapper delete() {
        builder.delete();
        return this;
    }

    public OkHttpClientWrapper delete(RequestBody body) {
        builder.delete(body.create());
        return this;
    }

    public static void setProxy(String hostname, int port) {
        InetSocketAddress address = new InetSocketAddress(hostname, port);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        client.newBuilder().proxy(proxy);
    }

    public static void cleanProxy() {
        client.newBuilder().proxy(Proxy.NO_PROXY);
    }


    /**
     * 问题：用户访问某个域名，可能会持续出现java.net.SocketTimeoutException
     * <p>
     * 猜测：可能是okhttp缓存的原因
     * <p>
     * 解决方案：重置okhttpclient
     * TODO: 可以通过RetryInterception解决
     * @return
     * @throws IOException
     */
    public Response execute() throws YunHttpIOException {
        okhttp3.Request request = builder.build();
        try {
            return new Response(client.newCall(request).execute());
        } catch (IOException e) {
            OkHttpClientWrapper.resetClient();
            try {
                return new Response(client.newCall(request).execute());
            } catch (IOException e1) {
                throw new YunHttpIOException(e);
            }
        }
    }
}
