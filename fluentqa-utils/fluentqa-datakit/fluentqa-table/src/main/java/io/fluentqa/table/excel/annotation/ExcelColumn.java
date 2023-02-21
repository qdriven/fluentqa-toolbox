package io.fluentqa.table.excel.annotation;


import io.fluentqa.table.excel.converter.ValueConverter;
import io.fluentqa.table.excel.converter.NoConvertion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {

    String title() default "";

    int index() default -1;

    String datePattern() default "";

    Class<? extends ValueConverter> converter() default NoConvertion.class;

    int width() default -1;

}
