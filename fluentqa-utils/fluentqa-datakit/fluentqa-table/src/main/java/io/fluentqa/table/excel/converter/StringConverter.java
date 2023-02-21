package io.fluentqa.table.excel.converter;

public class StringConverter implements ValueConverter<String, String> {

    @Override
    public String convert(String value) {
        return value;
    }

}
