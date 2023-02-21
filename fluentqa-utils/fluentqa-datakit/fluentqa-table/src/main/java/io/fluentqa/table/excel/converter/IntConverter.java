package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.base.exception.ConverterException;

public class IntConverter extends NumberConverter implements ValueConverter<String, Integer> {

    @Override
    public Integer convert(String value) throws ConverterException {
        try {
            value = super.replaceComma(value);
            if (StringUtil.isEmpty(value)) {
                return null;
            }

            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new ConverterException("convert [" + value + "] to Integer error", e);
        }
    }

}