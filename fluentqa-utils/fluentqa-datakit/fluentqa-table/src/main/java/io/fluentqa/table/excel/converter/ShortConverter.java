package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.base.exception.ConverterException;

public class ShortConverter extends NumberConverter implements ValueConverter<String, Short> {

    @Override
    public Short convert(String value) throws ConverterException {
        try {
            value = super.replaceComma(value);
            if (StringUtil.isEmpty(value)) {
                return 0;
            }
            return Short.parseShort(value);
        } catch (Exception e) {
            throw new ConverterException("convert [" + value + "] to Integer error", e);
        }
    }

}