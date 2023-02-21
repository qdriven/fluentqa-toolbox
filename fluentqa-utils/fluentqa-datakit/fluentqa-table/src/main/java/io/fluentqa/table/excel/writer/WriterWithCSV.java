package io.fluentqa.table.excel.writer;


import io.fluentqa.table.FluentExcelWriter;
import io.fluentqa.table.base.exception.WriterException;
import io.fluentqa.table.excel.annotation.ExcelColumn;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * CSV Writer
 *
 */
public class WriterWithCSV extends ExcelWriter {

    private OutputStream   outputStream;
    private List<String[]> csvLines;

    public WriterWithCSV(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void writeSheet(FluentExcelWriter writer) throws WriterException {
        Collection<?> rows = writer.rows();
        this.parseCSVLines(rows);

        try (OutputStreamWriter osWriter =
                     new OutputStreamWriter(outputStream, writer.charset())) {


            osWriter.write('\ufeff');

            for (String[] csvLine : csvLines) {
                writeLine(osWriter, csvLine);
            }
        } catch (Exception e) {
            throw new WriterException(e);
        }
    }

    private void writeLine(OutputStreamWriter osWriter, String[] csvLine) throws IOException {
        for (int i = 0; i < csvLine.length; i++) {
            String col = csvLine[i];
            col = null == col ? "" : col;
            osWriter.write(col);
            if (i < csvLine.length - 1) {
                osWriter.write(",");
            }
        }
        osWriter.write("\n");
    }

    private void parseCSVLines(Collection<?> rows) {
        csvLines = new ArrayList<>(rows.size());

        Class<?>            type         = rows.iterator().next().getClass();
        Field[]             fields       = type.getDeclaredFields();
        Map<Integer, Field> fieldIndexes = new HashMap<>(fields.length);

        for (Field field : fields) {
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            if (null != column) {
                field.setAccessible(true);
                fieldIndexes.put(column.index(), field);
            }
        }

        List<Field> sortedFields = fieldIndexes.keySet().stream()
                .sorted().map(fieldIndexes::get)
                .collect(toList());

        for (Object item : rows) {
            try {
                String[] row = parseRow(sortedFields, item);
                csvLines.add(row);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] parseRow(List<Field> sortedFields, Object item) throws Exception {
        String[] row  = new String[sortedFields.size()];
        int      size = sortedFields.size();
        for (int i = 0; i < size; i++) {
            Object value = sortedFields.get(i).get(item);
            if (null == value) {
                row[i] = "";
            } else {
                row[i] = this.computeColumnContent(value, sortedFields.get(i));
            }
        }
        return row;
    }


}
