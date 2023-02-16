package io.qmeta.wps.common.http.interceptors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class RetryInterceptor implements Interceptor {

    private final int retryTimes;

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request request = chain.request();
        Response response = doRequest(chain, request);
        int tryCount = 0;
        while (response == null && tryCount < retryTimes) {
            Request newRequest = request.newBuilder().build();
            response = doRequest(chain, newRequest);
            tryCount++;
            log.warn("Request failed, retry to acquire a new connection, {} in {} times", tryCount, retryTimes);
        }
        if (response == null) { // Important ,should throw an exception here
            throw new IOException();
        }
        return response;
    }

    private Response doRequest(Chain chain, Request request) {
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            log.error("", e);
        }
        return response;
    }
}
