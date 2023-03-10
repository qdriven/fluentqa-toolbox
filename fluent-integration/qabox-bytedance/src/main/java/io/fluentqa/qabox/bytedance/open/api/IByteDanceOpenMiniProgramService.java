package io.fluentqa.qabox.bytedance.open.api;

import com.google.common.collect.Multimap;

/**
 * @Authorn
 * @date 2020/09/21
 **/
public interface IByteDanceOpenMiniProgramService {

    String getAccessToken();

    String getAccessToken(boolean forceRefresh);

    String get(String url);

    <T> T get(String url, Class<T> t);

    <T> T post(String url, Object request, Class<T> t);

    <T> T postWithHeaders(String url, Multimap<String, String> headers, Object request, Class<T> t);
}
