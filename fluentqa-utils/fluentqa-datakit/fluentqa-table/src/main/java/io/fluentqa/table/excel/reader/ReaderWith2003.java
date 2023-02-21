package io.fluentqa.table.excel.reader;
import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.base.exception.ConverterException;
import io.fluentqa.table.base.exception.ReaderException;
import io.fluentqa.table.excel.converter.ValueConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;
import io.fluentqa.table.FluentExcelReader;
@Slf4j
public class ReaderWith2003 extends ReaderConverter implements ExcelReader {

    private Workbook workbook;

    public ReaderWith2003(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public <T> Stream<T> readExcel(FluentExcelReader FluentReader) throws ReaderException {
        Class             type    = FluentReader.modelType();
        Stream.Builder<T> builder = Stream.builder();
        try {
            this.initFieldConverter(type.getDeclaredFields());
            Sheet sheet = getSheet(FluentReader);

            int startRow = FluentReader.startRow();
            int totalRow = sheet.getPhysicalNumberOfRows();

            for (int i = 0; i < totalRow; i++) {
                if (i < startRow) {
                    continue;
                }
                Row row = sheet.getRow(i);
                if (null == row) {
                    continue;
                }

                Object instance = type.newInstance();
                for (Field field : fieldIndexes.values()) {
                    this.writeFiledValue(row, instance, field);
                }
                builder.add((T) instance);
            }
            return builder.build();
        } catch (Exception e) {
            throw new ReaderException(e);
        }
    }

    public Sheet getSheet(FluentExcelReader FluentReader) {
        return StringUtil.isNotEmpty(FluentReader.sheetName()) ?
                workbook.getSheet(FluentReader.sheetName()) : workbook.getSheetAt(FluentReader.sheetIndex());
    }

    public Object getCellValue(Field field, Cell cell) throws ConverterException {
        ValueConverter<String, ?> converter = fieldConverters.get(field);

        if (null == converter) {
            return cell.getStringCellValue();
        }
        if (cell.getCellType() != CellType.NUMERIC) {
            return converter.convert(cell.getStringCellValue());
        }
        if (isDateType(field.getType())) {
            Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
            if (field.getType().equals(Date.class)) {
                return javaDate;
            } else if (field.getType().equals(LocalDate.class)) {
                return javaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } else if (field.getType().equals(LocalDateTime.class)) {
                return javaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
            return null;
        } else {
            return converter.convert(cell.getNumericCellValue() + "");
        }
    }

    private boolean isDateType(Class<?> type) {
        return Date.class.equals(type) || LocalDate.class.equals(type) || LocalDateTime.class.equals(type);
    }

}
