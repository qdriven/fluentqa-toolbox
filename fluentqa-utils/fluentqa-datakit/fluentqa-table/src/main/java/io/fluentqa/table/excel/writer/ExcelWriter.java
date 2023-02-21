package io.fluentqa.table.excel.writer;

import io.fluentqa.table.base.Constant;
import io.fluentqa.table.FluentExcelWriter;
import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.base.exception.WriterException;
import io.fluentqa.table.excel.converter.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import io.fluentqa.table.excel.annotation.ExcelColumn;
import static java.util.Comparator.comparingInt;

/**
 * ExcelWriter
 *
 */
@Slf4j
public abstract class ExcelWriter {

    private int rowNum;
    private Sheet sheet;
    private Map<Integer, Field> fieldIndexes;
    private List<ExcelColumn> columns;

    Workbook workbook;
    OutputStream outputStream;

    ExcelWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    ExcelWriter() {
    }

    /**
     * Write data to Excel Sheet
     * <p>
     * 1. create sheet
     * 2. write title(optional)
     * 3. write column header
     * 4. write row
     * 5. write to OutputStream
     *
     * @param writer excel-plus writer
     * @throws WriterException
     */
    void writeSheet(FluentExcelWriter writer) throws WriterException {
        // create sheet
        this.sheet = workbook.createSheet(writer.sheetName());

        // setting styles
        CellStyle headerStyle = null;
        CellStyle columnStyle = null;
        CellStyle titleStyle = null;
        if (null != writer.headerStyle()) {
            headerStyle = writer.headerStyle().accept(workbook, workbook.createCellStyle());
        }
        if (null != writer.cellStyle()) {
            columnStyle = writer.cellStyle().accept(workbook, workbook.createCellStyle());
        }
        if (null != writer.titleStyle()) {
            titleStyle = writer.titleStyle().accept(workbook, workbook.createCellStyle());
        }

        if (writer.isRaw()) {
            writer.sheetConsumer().accept(sheet);
        } else {
            // compute the Filed to be written
            Collection<?> rows = writer.rows();
            Field[] fields = rows.iterator().next().getClass().getDeclaredFields();

            this.fieldIndexes = new HashMap<>(fields.length);
            this.columns = new ArrayList<>();

            for (Field field : fields) {
                ExcelColumn column = field.getAnnotation(ExcelColumn.class);
                if (null != column) {
                    field.setAccessible(true);
                    fieldIndexes.put(column.index(), field);
                    columns.add(column);
                }
            }

            int colRowIndex = 0;
            // write title
            String title = writer.headerTitle();
            if (StringUtil.isNotEmpty(title)) {
                Integer maxColIndex = columns.stream()
                        .map(ExcelColumn::index)
                        .max(comparingInt(Integer::intValue))
                        .get();

                this.writeHeader(titleStyle, sheet, title, maxColIndex);
                colRowIndex = 1;
            }

            this.rowNum = writer.startRow();
            if (this.rowNum == 0) {
                this.rowNum = colRowIndex + 1;
            }

            try {
                // write column header
                this.writeColumnNames(colRowIndex, headerStyle);

                // write rows
                for (Object row : rows) {
                    this.writeRow(row, columnStyle);
                }
            } catch (Exception e) {
                log.error("write row fail", e);
            }
        }

        // write to OutputStream
        try (OutputStream os = outputStream) {
            workbook.write(os);
            if (workbook instanceof SXSSFWorkbook) {
                ((SXSSFWorkbook) workbook).dispose();
            }
        } catch (Exception e) {
            throw new WriterException("workbook write to OutputStream error", e);
        }
    }

    private void writeHeader(CellStyle cellStyle, Sheet sheet, String title, int maxColIndex) {
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(50);

        for (int i = 0; i <= maxColIndex; i++) {
            Cell cell = titleRow.createCell(i);
            if (i == 0) {
                cell.setCellValue(title);
            }
            if (null != cellStyle) {
                cell.setCellStyle(cellStyle);
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, maxColIndex));
    }

    private void writeColumnNames(int rowIndex, CellStyle headerStyle) {
        Row rowHead = sheet.createRow(rowIndex);
        for (ExcelColumn column : columns) {
            Cell cell = rowHead.createCell(column.index());
            if (null != headerStyle) {
                cell.setCellStyle(headerStyle);
            }
            cell.setCellValue(column.title());
            if (column.width() > 0) {
                sheet.setColumnWidth(column.index(), column.width());
            } else {
                sheet.setColumnWidth(column.index(), Constant.DEFAULT_COLUMN_WIDTH);
            }
        }
    }

    private void writeRow(Object instance, CellStyle columnStyle) throws Exception {
        Row row = sheet.createRow(rowNum++);
        for (Integer index : fieldIndexes.keySet()) {
            Field field = fieldIndexes.get(index);
            if (null == field) {
                continue;
            }

            Object value = field.get(instance);
            if (value == null) {
                continue;
            }

            Cell cell = row.createCell(index);
            if (null != columnStyle) {
                cell.setCellStyle(columnStyle);
            }

            String fieldValue = computeColumnContent(value, field);
            cell.setCellValue(fieldValue);
        }
    }

    String computeColumnContent(Object value, Field field) throws Exception {
        if (field.getType().equals(String.class)) {
            return value.toString();
        }
        ExcelColumn column = field.getAnnotation(ExcelColumn.class);
        if (!NoConvertion.class.equals(column.converter())) {
            ValueConverter convert = column.converter().newInstance();
            ConverterCache.addConvert(convert);
            return convert.toString(value);
        } else {
            if (StringUtil.isNotEmpty(column.datePattern())) {
                String content = "";
                if (Date.class.equals(field.getType())) {
                    content = new DateConverter(column.datePattern()).toString((Date) value);
                } else if (LocalDate.class.equals(field.getType())) {
                    content = new LocalDateConverter(column.datePattern()).toString((LocalDate) value);
                }
                if (LocalDateTime.class.equals(field.getType())) {
                    content = new LocalDateTimeConverter(column.datePattern()).toString((LocalDateTime) value);
                }
                return content;
            } else {
                ValueConverter converter = ConverterCache.computeConvert(field);
                if (null != converter) {
                    return converter.toString(value);
                } else {
                    return value.toString();
                }
            }
        }
    }

}
