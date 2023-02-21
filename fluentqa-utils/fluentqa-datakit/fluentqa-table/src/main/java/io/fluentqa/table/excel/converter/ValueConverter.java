package io.fluentqa.table.excel.converter;

import io.fluentqa.table.base.exception.ConverterException;
import org.apache.logging.log4j.util.Strings;

public interface ValueConverter<INPUT, R> {

    R convert(INPUT value) throws ConverterException;

    default String toString(R fieldValue) throws ConverterException {
        if (null == fieldValue) {
            return Strings.EMPTY;
        }
        return fieldValue.toString();
    }

}