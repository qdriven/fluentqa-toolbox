package io.qmeta.wps.common.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RetrofitWrapper {

    private static HttpLoggingInterceptor loggingInterceptor =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    //    private static RetryInterceptor retryInterceptor = new RetryInterceptor(3);
    private static OkHttpClient defaultHttpClient = defaultHttpClient();
    private static Map<String, Retrofit> retrofitMap = new ConcurrentHashMap<>();

    public static Retrofit getRetrofit(String baseUrl) {
        if (retrofitMap.get(baseUrl) == null) {
            retrofitMap.put(baseUrl, new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .client(defaultHttpClient)
                    .build());
        }
        return retrofitMap.get(baseUrl);

    }

    public static OkHttpClient defaultHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
//                .addInterceptor(retryInterceptor)
                .retryOnConnectionFailure(false)
                .connectTimeout(Duration.ofSeconds(15))
                .build();
    }

    public static <T> Response<T> send(Call<T> request) {
        try {
            return request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T sendAndReturnBody(Call<T> request) {
        try {
            return request.execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
