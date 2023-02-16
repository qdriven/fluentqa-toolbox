package io.fluentqa.qabox.bytedance.open.api.v1.impl;

import io.fluentqa.qabox.bytedance.open.api.v1.IByteDanceOpenV1MiniProgramPayService;
import io.fluentqa.qabox.bytedance.open.api.v1.IByteDanceOpenV1MiniProgramService;
import io.fluentqa.qabox.bytedance.open.api.v1.request.pay.OrderPayAddRequest;
import io.fluentqa.qabox.bytedance.open.api.v1.request.pay.OrderPayDeleteRequest;
import io.fluentqa.qabox.bytedance.open.api.v1.request.pay.OrderPayUpdateRequest;
import io.fluentqa.qabox.bytedance.open.api.v1.response.pay.OrderPayAddResponse;
import io.fluentqa.qabox.bytedance.open.api.v1.response.pay.OrderPayDeleteResponse;
import io.fluentqa.qabox.bytedance.open.api.v1.response.pay.OrderPayUpdateResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Authorn
 * @date 2021/06/18
 **/
@Slf4j
public class ByteDanceOpenV1MiniProgramPayServiceImpl implements IByteDanceOpenV1MiniProgramPayService {

    private IByteDanceOpenV1MiniProgramService byteDanceOpenV1MiniProgramService;

    public ByteDanceOpenV1MiniProgramPayServiceImpl(IByteDanceOpenV1MiniProgramService byteDanceOpenV1MiniProgramService) {
        this.byteDanceOpenV1MiniProgramService = byteDanceOpenV1MiniProgramService;
    }

    @Override
    public OrderPayAddResponse orderAdd(OrderPayAddRequest request) {
        return byteDanceOpenV1MiniProgramService.post(ORDER_ADD_URL, request, OrderPayAddResponse.class);
    }

    @Override
    public OrderPayUpdateResponse orderUpdate(OrderPayUpdateRequest request) {
        return byteDanceOpenV1MiniProgramService.post(ORDER_UPDATE_URL, request, OrderPayUpdateResponse.class);
    }

    @Override
    public OrderPayDeleteResponse orderDelete(OrderPayDeleteRequest request) {
        return byteDanceOpenV1MiniProgramService.post(ORDER_DELETE_URL, request, OrderPayDeleteResponse.class);
    }
}
