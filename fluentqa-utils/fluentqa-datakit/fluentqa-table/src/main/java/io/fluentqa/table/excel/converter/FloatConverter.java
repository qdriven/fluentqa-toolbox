package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.base.exception.ConverterException;


public class FloatConverter extends NumberConverter implements ValueConverter<String, Float> {

    @Override
    public Float convert(String value) throws ConverterException {
        try {
            value = super.replaceComma(value);
            if (StringUtil.isEmpty(value)) {
                return null;
            }
            return Float.parseFloat(value);
        } catch (Exception e){
            throw new ConverterException("convert [" + value + "] to Float error", e);
        }
    }

}
