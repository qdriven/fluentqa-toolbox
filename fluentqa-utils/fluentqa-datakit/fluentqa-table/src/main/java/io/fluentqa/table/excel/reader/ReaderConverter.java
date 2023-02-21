package io.fluentqa.table.excel.reader;

import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.excel.annotation.ExcelColumn;
import io.fluentqa.table.excel.converter.ConverterCache;
import io.fluentqa.table.excel.converter.NoConvertion;
import io.fluentqa.table.excel.converter.ValueConverter;
import io.fluentqa.table.base.exception.ConverterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class ReaderConverter {

    Map<Integer, Field> fieldIndexes;
    Map<String,Field> fieldColumns;
    Map<Field, ValueConverter<String, ?>> fieldConverters;

    void initFieldConverter(Field[] fields) throws Exception {
        this.fieldConverters = new HashMap<>();
        this.fieldIndexes = new HashMap<>(fields.length);
        this.fieldColumns = new HashMap<>(fields.length);

        for (Field field : fields) {
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            //TODO: if ExcelColumn Doesn't exist,use field name to mapping
            if (null == column) {
                continue;
            }
            field.setAccessible(true);
            fieldIndexes.put(column.index(), field);
            if(StringUtil.isEmpty(column.title())){
                fieldColumns.put(field.getName(),field);
            }else{
                fieldColumns.put(column.title(),field);
            }
            ValueConverter converter;
            if (NoConvertion.class.equals(column.converter())) {
                converter = ConverterCache.computeConvert(field);
            } else {
                converter = column.converter().newInstance();
            }
            if (null != converter) {
                fieldConverters.put(field, converter);
            }
        }
    }

    void writeFiledValue(Row row, Object instance, Field field) {
        ExcelColumn column = field.getAnnotation(ExcelColumn.class);
        Cell        cell   = row.getCell(column.index());
        if (null == cell) {
            return;
        }
        try {
            Object cellValue = getCellValue(field, cell);
            field.set(instance, cellValue);
        } catch (Exception e) {
            log.error("write value {} to field {} failed", cell.getStringCellValue(), field.getName(), e);
        }
    }

    public Object getCellValue(Field field, Cell cell) throws ConverterException {
        return cell.getStringCellValue();
    }

}
