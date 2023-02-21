package io.fluentqa.table.excel.reader;


import io.fluentqa.table.FluentExcelReader;
import io.fluentqa.table.base.exception.ReaderException;

import java.util.stream.Stream;

public interface ExcelReader {

    <T> Stream<T> readExcel(FluentExcelReader reader) throws ReaderException;

}
