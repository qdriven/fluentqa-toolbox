package io.fluentqa.qabox.bytedance.open.api.impl;

import io.fluentqa.qabox.bytedance.open.api.IByteDanceOpenService;
import io.fluentqa.qabox.bytedance.open.api.v1.IByteDanceOpenV1ComponentService;
import io.fluentqa.qabox.bytedance.open.api.IByteDanceOpenConfigStorage;
import io.fluentqa.qabox.bytedance.common.redis.IByteDanceRedisOps;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceHttpClient;
import io.fluentqa.qabox.bytedance.open.api.v2.IByteDanceOpenV2ComponentService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;

/**
 * @Authorn
 * @date 2020/06/19
 **/
@Slf4j
public class ByteDanceOpenServiceImpl implements IByteDanceOpenService {

    @Getter
    @Setter
    private IByteDanceHttpClient byteDanceHttpClient;

    @Getter
    @Setter
    private IByteDanceOpenV1ComponentService byteDanceOpenV1ComponentService;

    @Getter
    @Setter
    private IByteDanceOpenV2ComponentService byteDanceOpenV2ComponentService;

    @Getter
    @Setter
    private IByteDanceOpenConfigStorage byteDanceOpenConfigStorage;

    @Getter
    @Setter
    private RedissonClient redissonClient;

    @Getter
    @Setter
    private IByteDanceRedisOps byteDanceRedisOps;
}
