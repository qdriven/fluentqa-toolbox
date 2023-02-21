package io.fluentqa.table.excel.reader;

import io.fluentqa.table.base.ExcelUtil;
import io.fluentqa.table.FluentExcelReader;
import io.fluentqa.table.base.exception.ReaderException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class ReaderFactory {

    public static <T> Stream<T> readByFile(FluentExcelReader FluentReader) {
        if (ExcelUtil.isXLSX(FluentReader.fromFile())) {
            return new ReaderWith2007(null).readExcel(FluentReader);
        } else {
            if (ExcelUtil.isCSV(FluentReader.fromFile())) {
                try {
                    return new ReaderWithCSV(new FileInputStream(FluentReader.fromFile())).readExcel(FluentReader);
                } catch (FileNotFoundException e) {
                    throw new ReaderException(FluentReader.fromFile().getName() + " not found", e);
                }
            } else if (ExcelUtil.isXLS(FluentReader.fromFile())) {
                return new ReaderWith2003(ExcelUtil.create(FluentReader.fromFile())).readExcel(FluentReader);
            } else {
                throw new ReaderException(FluentReader.fromFile().getName() + " is the wrong format");
            }
        }
    }

    public static <T> Stream<T> readByStream(FluentExcelReader FluentReader) throws ReaderException {
        byte[] bytes;
        try {
            bytes = ExcelUtil.streamAsBytes(FluentReader.fromStream());
        } catch (IOException e) {
            throw new ReaderException(e);
        }

        if (ExcelUtil.isXLSX(new ByteArrayInputStream(bytes))) {
            FluentReader.from(new ByteArrayInputStream(bytes));
            return new ReaderWith2007(null).readExcel(FluentReader);
        } else {
            if (ExcelUtil.isXLS(new ByteArrayInputStream(bytes))) {
                return new ReaderWith2003(ExcelUtil.create(new ByteArrayInputStream(bytes))).readExcel(FluentReader);
            } else {
                return new ReaderWithCSV(new ByteArrayInputStream(bytes)).readExcel(FluentReader);
            }
        }
    }

}
