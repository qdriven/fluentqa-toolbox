package io.fluentqa.qabox.bytedance.open.api.v1.response.appinfo;

import io.fluentqa.qabox.bytedance.common.error.ByteDanceError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/07/20
 **/
@Data
public class AppModifyServerDomainResponse extends ByteDanceError implements IByteDanceResponse {

    private static final long serialVersionUID = -4787789592142103564L;

    private DataObj data;

    @Data
    public static class DataObj implements Serializable {

        private static final long serialVersionUID = -48525128509077904L;

        private List<String> request;

        private List<String> socket;

        private List<String> upload;

        private List<String> download;

    }
}
