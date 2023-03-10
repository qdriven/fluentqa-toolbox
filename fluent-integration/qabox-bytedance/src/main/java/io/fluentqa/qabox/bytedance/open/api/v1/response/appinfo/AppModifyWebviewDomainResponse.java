package io.fluentqa.qabox.bytedance.open.api.v1.response.appinfo;

import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import java.util.List;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/07/23
 **/
@Data
public class AppModifyWebviewDomainResponse extends ByteDanceError implements IByteDanceResponse {

    private static final long serialVersionUID = 5482713935876030732L;

    private List<String> data;
}
