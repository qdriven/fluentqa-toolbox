package io.fluentqa.table.excel.converter;


import io.fluentqa.table.base.exception.ConverterException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateConverter implements ValueConverter<String, LocalDate> {

    private DateTimeFormatter formatter;

    public LocalDateConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalDate convert(String value) throws ConverterException {
        try {
            if(null == value){
                return null;
            }
            return LocalDate.parse(value, formatter);
        } catch (DateTimeParseException e) {
            try {
                YearMonth ym = YearMonth.parse(value, formatter);
                return ym.atDay(1);
            } catch (Exception e2) {
                throw new ConverterException("convert [" + value + "] to LocalDate error", e2);
            }
        } catch (Exception e) {
            throw new ConverterException("convert [" + value + "] to LocalDate error", e);
        }
    }

    @Override
    public String toString(LocalDate localDate) {
        if(null == localDate){
            return null;
        }
        return localDate.format(formatter);
    }

}
