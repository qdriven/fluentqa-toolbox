package io.fluentqa.qabox.bytedance.open.api;


import io.fluentqa.qabox.bytedance.common.redis.IByteDanceRedisOps;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceHttpClient;
import io.fluentqa.qabox.bytedance.open.api.v1.IByteDanceOpenV1ComponentService;
import io.fluentqa.qabox.bytedance.open.api.v2.IByteDanceOpenV2ComponentService;

/**
 * @Authorn
 * @date 2020/06/19
 **/
public interface IByteDanceOpenService{


    IByteDanceOpenV1ComponentService getByteDanceOpenV1ComponentService();

    void setByteDanceOpenV1ComponentService(IByteDanceOpenV1ComponentService byteDanceOpenComponentService);

    IByteDanceOpenV2ComponentService getByteDanceOpenV2ComponentService();

    void setByteDanceOpenV2ComponentService(IByteDanceOpenV2ComponentService byteDanceOpenComponentService);

    IByteDanceOpenConfigStorage getByteDanceOpenConfigStorage();

    void setByteDanceOpenConfigStorage(IByteDanceOpenConfigStorage openConfigStorage);

    IByteDanceHttpClient getByteDanceHttpClient();

    void setByteDanceHttpClient(IByteDanceHttpClient byteDanceHttpClient);

    IByteDanceRedisOps getByteDanceRedisOps();

    void setByteDanceRedisOps(IByteDanceRedisOps byteDanceRedisOps);
}
