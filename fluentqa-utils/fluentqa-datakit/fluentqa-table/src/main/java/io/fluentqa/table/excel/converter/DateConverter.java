package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.exception.ConverterException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date to string converter
 */
public class DateConverter implements ValueConverter<String, Date> {

    private ThreadLocal<DateFormat> df;

    public DateConverter(String pattern) {
        this.df = ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern));
    }

    @Override
    public Date convert(String value) throws ConverterException {
        try {
            if(null == value){
                return null;
            }
            return df.get().parse(value);
        } catch (Exception e) {
            throw new ConverterException("convert [" + value + "] to Date error", e);
        }
    }

    @Override
    public String toString(Date date) throws ConverterException {
        try {
            if(null == date){
                return null;
            }
            return df.get().format(date);
        } catch (Exception e) {
            throw new ConverterException("convert [" + date + "] to String error", e);
        }
    }

}
