package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.base.exception.ConverterException;

public class DoubleConverter extends NumberConverter implements ValueConverter<String, Double> {

    @Override
    public Double convert(String value) throws ConverterException {
        try {
            value = super.replaceComma(value);
            if (StringUtil.isEmpty(value)) {
                return null;
            }
            return Double.parseDouble(value);
        } catch (Exception e){
            throw new ConverterException("convert [" + value + "] to Double error", e);
        }
    }

}
