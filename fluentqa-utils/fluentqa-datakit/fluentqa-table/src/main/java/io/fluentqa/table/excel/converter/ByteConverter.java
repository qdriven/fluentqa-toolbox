package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.exception.ConverterException;

public class ByteConverter implements ValueConverter<String, Byte> {

    @Override
    public Byte convert(String value) throws ConverterException {
        try {
            return Byte.parseByte(value);
        } catch (Exception e){
            throw new ConverterException("convert [" + value + "] to Byte error", e);
        }
    }

}