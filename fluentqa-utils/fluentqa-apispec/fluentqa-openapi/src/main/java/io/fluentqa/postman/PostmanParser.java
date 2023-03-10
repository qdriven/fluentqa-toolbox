package io.fluentqa.postman;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import io.fluentqa.postman.model.PostmanCollection;

import java.io.File;
import java.nio.charset.Charset;

public class PostmanParser {

    public PostmanCollection toPostmanCollection(String jsonString){
        return JSONUtil.toBean(jsonString,PostmanCollection.class);
    }

    public PostmanCollection toPostmanCollectionFromFile(String filePath){
        JSON json = JSONUtil.readJSON(new File(filePath), Charset.defaultCharset());
        return JSONUtil.toBean(json,PostmanCollection.class,true);
    }

    public static PostmanParser create(){
        return new PostmanParser();
    }
}
