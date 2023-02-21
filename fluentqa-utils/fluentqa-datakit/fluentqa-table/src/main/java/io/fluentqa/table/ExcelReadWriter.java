package io.fluentqa.table;

import io.fluentqa.table.base.TableType;

import java.io.File;
import java.util.List;

/**
 * TODO: Column Mode
 * https://hellokaton.github.io/excel-plus
 */
public class ExcelReadWriter {

    public <T> List<T> read(String filePath, Class<T> clazz) {
        return FluentExcelReader.create(clazz,
                        new File(filePath))
                .asList();
    }

    public <T> void write(String filePath, Class<T> clazz, List<T> dataSet) {
        FluentExcelWriter.create(TableType.XLSX).withRows(
                dataSet
        ).to(new File(filePath));
    }

    public <T> void writeCSV(String filePath, Class<T> clazz, List<T> dataSet) {
        FluentExcelWriter.create(TableType.CSV).withRows(
                dataSet
        ).to(new File(filePath));
    }
}
