package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.exception.ConverterException;
import io.fluentqa.table.base.StringUtil;

import java.math.BigDecimal;

public class DecimalConverter extends NumberConverter implements ValueConverter<String, BigDecimal> {

    @Override
    public BigDecimal convert(String value) throws ConverterException {
        try {
            value = super.replaceComma(value);
            if (StringUtil.isEmpty(value)) {
                return null;
            }
            return new BigDecimal(value);
        } catch (Exception e){
            throw new ConverterException("convert [" + value + "] to BigDecimal error", e);
        }
    }

}
