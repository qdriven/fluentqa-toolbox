package io.fluentqa.table.excel.converter;

public class BooleanConverter implements ValueConverter<String, Boolean> {

    @Override
    public Boolean convert(String value) {
        return Boolean.parseBoolean(value);
    }

}