package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.base.exception.ConverterException;



import java.math.BigInteger;

public class BigIntConverter extends NumberConverter implements ValueConverter<String, BigInteger> {

    @Override
    public BigInteger convert(String value) throws ConverterException {
        try {
            value = replaceComma(value);
            if (StringUtil.isEmpty(value)) {
                return null;
            }
            return new BigInteger(value);
        } catch (Exception e){
            throw new ConverterException("convert [" + value + "] to BigInteger error", e);
        }
    }

}
