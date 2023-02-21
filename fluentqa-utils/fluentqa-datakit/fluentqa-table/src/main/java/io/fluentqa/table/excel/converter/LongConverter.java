package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.base.exception.ConverterException;

public class LongConverter extends NumberConverter implements ValueConverter<String, Long> {

    @Override
    public Long convert(String value) throws ConverterException {
        try {
            value = super.replaceComma(value);
            if (StringUtil.isEmpty(value)) {
                return null;
            }
            return Long.parseLong(value);
        } catch (Exception e){
            throw new ConverterException("convert [" + value + "] to Long error", e);
        }
    }

}
