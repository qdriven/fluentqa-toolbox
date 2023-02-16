package io.fluentqa.qabox.bytedance.open.api.v1.impl;


import io.fluentqa.qabox.bytedance.open.api.IByteDanceOpenService;
import io.fluentqa.qabox.bytedance.open.api.v1.IByteDanceOpenV1TemplateService;
import io.fluentqa.qabox.bytedance.open.api.v1.request.template.TemplateAddTplRequest;
import io.fluentqa.qabox.bytedance.open.api.v1.request.template.TemplateDelTplRequest;
import io.fluentqa.qabox.bytedance.open.api.v1.response.template.TemplateAddTplResponse;
import io.fluentqa.qabox.bytedance.open.api.v1.response.template.TemplateDelTplResponse;
import io.fluentqa.qabox.bytedance.open.api.v1.response.template.TemplateGetDraftListResponse;
import io.fluentqa.qabox.bytedance.open.api.v1.response.template.TemplateGetTplListResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Authorn
 * @date 2020/07/01
 **/
@Slf4j
public class ByteDanceOpenV1TemplateServiceImpl implements IByteDanceOpenV1TemplateService {

    private IByteDanceOpenService byteDanceOpenService;

    public ByteDanceOpenV1TemplateServiceImpl(IByteDanceOpenService byteDanceOpenService) {
        this.byteDanceOpenService = byteDanceOpenService;
    }

    @Override
    public TemplateGetTplListResponse getTplList() {
        return byteDanceOpenService.getByteDanceOpenV1ComponentService().get(GET_TPL_LIST_URL, TemplateGetTplListResponse.class);
    }

    @Override
    public TemplateGetDraftListResponse getDraftList() {
        return byteDanceOpenService.getByteDanceOpenV1ComponentService().get(GET_DRAFT_LIST_URL, TemplateGetDraftListResponse.class);
    }

    @Override
    public TemplateAddTplResponse addTpl(TemplateAddTplRequest request) {
        return byteDanceOpenService.getByteDanceOpenV1ComponentService().post(ADD_TPL_URL, request, TemplateAddTplResponse.class);
    }

    @Override
    public TemplateDelTplResponse delTpl(TemplateDelTplRequest request) {
        return byteDanceOpenService.getByteDanceOpenV1ComponentService().post(DEL_TPL_URL, request, TemplateDelTplResponse.class);
    }
}
