package io.fluentqa.postman.model;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PostmanRequest {

    private String method;
    private String schema;
    private List<PostmanKeyValue> header;
    private PostmanBody body;
    private Map auth;
    private PostmanUrl url;
    private String description;
}
