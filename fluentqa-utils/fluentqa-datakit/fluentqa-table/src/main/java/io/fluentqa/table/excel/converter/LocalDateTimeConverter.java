package io.fluentqa.table.excel.converter;
import io.fluentqa.table.base.exception.ConverterException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements ValueConverter<String, LocalDateTime> {

    private DateTimeFormatter formatter;

    public LocalDateTimeConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalDateTime convert(String value) throws ConverterException {
        try {
            if (null == value) {
                return null;
            }
            return LocalDateTime.parse(value, formatter);
        } catch (Exception e) {
            throw new ConverterException("convert [" + value + "] to LocalDateTime error", e);
        }
    }

    @Override
    public String toString(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return localDateTime.format(formatter);
    }

}
