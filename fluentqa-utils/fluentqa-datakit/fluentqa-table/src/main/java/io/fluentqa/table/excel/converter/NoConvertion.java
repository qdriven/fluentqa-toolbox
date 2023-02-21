package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.exception.ConverterException;

public class NoConvertion implements ValueConverter {
    @Override
    public Object convert(Object value) throws ConverterException {
        return value;
    }
}
