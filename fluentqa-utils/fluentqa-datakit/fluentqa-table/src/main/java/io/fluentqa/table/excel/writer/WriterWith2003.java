package io.fluentqa.table.excel.writer;

import io.fluentqa.table.FluentExcelWriter;
import io.fluentqa.table.base.exception.WriterException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Excel Writer by 2003
 *
 */
public class WriterWith2003 extends ExcelWriter {

    public WriterWith2003(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void writeSheet(FluentExcelWriter writer) throws WriterException {
        if (writer.template() != null) {
            try {
                this.workbook = WorkbookFactory.create(writer.template());
                super.writeSheet(writer);
            } catch (IOException e) {
                throw new WriterException(e);
            }
        } else {
            this.workbook = new HSSFWorkbook();
            super.writeSheet(writer);
        }
    }

}
