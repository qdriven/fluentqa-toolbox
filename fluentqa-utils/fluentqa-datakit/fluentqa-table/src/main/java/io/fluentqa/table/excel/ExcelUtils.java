package io.fluentqa.table.excel;

import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.core.SaxExcelReader;
import io.fluentqa.table.excel.exception.ExcelException;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExcelUtils {

    public static <T> List<T> readExcel(String fileName, Class<T> clazz) {

        File file = new File(fileName);
        return SaxExcelReader.of(clazz).sheet(0).rowFilter(
                cells -> cells.getRowNum() > 0
        ).read(file);
    }

    public static <T> void writeToExcel(String fileName, List<T> dataSet, Class<T> clazz) {
        try (Workbook wb = DefaultExcelBuilder.of(clazz).build(dataSet)) {
            wb.write(Files.newOutputStream(Path.of(fileName)));
        } catch (IOException e) {
            throw new ExcelException(e);
        }
    }
}

