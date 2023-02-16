package io.qmeta.wps.yunfile.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RemainingSpaceResponse {

    private long remaining;
    private int result;
}
