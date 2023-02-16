package io.fluentqa.qabox.bytedance.open.api.v1;

import io.fluentqa.qabox.bytedance.open.api.v1.request.pay.OrderPayAddRequest;
import io.fluentqa.qabox.bytedance.open.api.v1.request.pay.OrderPayDeleteRequest;
import io.fluentqa.qabox.bytedance.open.api.v1.request.pay.OrderPayUpdateRequest;
import io.fluentqa.qabox.bytedance.open.api.v1.response.pay.OrderPayAddResponse;
import io.fluentqa.qabox.bytedance.open.api.v1.response.pay.OrderPayDeleteResponse;
import io.fluentqa.qabox.bytedance.open.api.v1.response.pay.OrderPayUpdateResponse;

/**
 * 代授权小程序业务-支付服务
 * @Authorn
 * @date 2021/06/12
 **/
public interface IByteDanceOpenV1MiniProgramPayService {

    /**
     * 新增订单
     */
    String ORDER_ADD_URL = "https://developer.toutiao.com/api/apps/order/add";

    /**
     * 更新订单
     */
    String ORDER_UPDATE_URL = "https://developer.toutiao.com/api/apps/order/update";

    /**
     * 删除订单
     */
    String ORDER_DELETE_URL = "https://developer.toutiao.com/api/apps/order/delete";

    /**
     * 新增订单
     * @param request
     * @return
     */
    OrderPayAddResponse orderAdd(OrderPayAddRequest request);

    /**
     * 更新订单
     * @param request
     * @return
     */
    OrderPayUpdateResponse orderUpdate(OrderPayUpdateRequest request);

    /**
     * 删除订单
     * @param request
     * @return
     */
    OrderPayDeleteResponse orderDelete(OrderPayDeleteRequest request);

}
