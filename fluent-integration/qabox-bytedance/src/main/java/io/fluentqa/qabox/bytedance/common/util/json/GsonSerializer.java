package io.fluentqa.qabox.bytedance.common.util.json;

import cn.hutool.core.util.StrUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @Authorn
 * @date 2020/08/03
 **/
public class GsonSerializer implements JsonSerializer {
    private static final Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create();

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T parse(String jsonString, Class<T> clazz) {
        if(clazz == String.class){
            return (T)jsonString;
        }
        return gson.fromJson(jsonString, clazz);
    }

    @Override
    public String getFieldAliasName(Field field){
        String fieldAliasName = field.getName();
        Annotation annotation = field.getAnnotation(SerializedName.class);
        if(annotation != null){
            SerializedName serializedNameAnnotation = (SerializedName)annotation;
            if(!StrUtil.isEmpty(serializedNameAnnotation.value())){
                fieldAliasName = serializedNameAnnotation.value();
            }
        }
        return fieldAliasName;
    }
}
