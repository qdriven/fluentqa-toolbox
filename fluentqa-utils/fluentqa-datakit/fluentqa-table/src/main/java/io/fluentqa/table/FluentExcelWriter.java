package io.fluentqa.table;

import io.fluentqa.table.base.Constant;
import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.base.StyleConsumer;
import io.fluentqa.table.base.TableType;
import io.fluentqa.table.base.exception.WriterException;
import io.fluentqa.table.excel.writer.WriterWith2003;
import io.fluentqa.table.excel.writer.WriterWith2007;
import io.fluentqa.table.excel.writer.WriterWithCSV;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * <p>
 * Used to write Excel documents
 *
 */
public class FluentExcelWriter {

    /**
     * The name of the Sheet to be written to Excel. The default is Sheet0.
     */
    private String sheetName = Constant.DEFAULT_SHEET_NAME;

    /**
     * Store the row to be written
     */
    private Collection<?> rows;

    /**
     * Write from the first few lines,
     * the default is automatic calculation, calculated by Excel title and column,
     * may be 1 or 2
     */
    private int startRow;

    /**
     * Buffer when writing a document in xlsx format
     */
    private int bufferSize = 100;

    private boolean withRaw;

    /**
     * Type of excel written, select XLSX, XLS, CSV
     */
    private TableType excelType;

    /**
     * Write the title of Excel, optional
     */
    private String headerTitle;

    /**
     * Specify the path to the template by writing data according to the specified template
     */
    private File template;

    /**
     * Custom title style
     */
    private StyleConsumer<Workbook, CellStyle> titleStyle;

    /**
     * Custom column header style
     */
    private StyleConsumer<Workbook, CellStyle> headerStyle;

    /**
     * Custom row column style
     */
    private StyleConsumer<Workbook, CellStyle> cellStyle;

    private Consumer<Sheet> sheetConsumer;

    private Charset charset = StandardCharsets.UTF_8;

    /**
     * if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     */
    private boolean isAppend = false;

    public static FluentExcelWriter create() {
        return new FluentExcelWriter(TableType.XLSX);
    }

    public static FluentExcelWriter create(TableType excelType) {
        return new FluentExcelWriter(excelType);
    }

    public FluentExcelWriter(TableType excelType) {
        this.excelType = excelType;
    }

    /**
     * Set the data to be written, receive a collection
     *
     * @param rows row data
     * @return Writer
     */
    public FluentExcelWriter withRows(Collection<?> rows) {
        this.rows = rows;
        return this;
    }

    /**
     * Configure the name of the sheet to be written. The default is Sheet0.
     *
     * @param sheetName sheet name
     * @return Writer
     */
    public FluentExcelWriter sheet(String sheetName) {
        if (StringUtil.isEmpty(sheetName)) {
            throw new IllegalArgumentException("sheet cannot be empty");
        }
        this.sheetName = sheetName;
        return this;
    }

    /**
     * Set the data to be written from the first few lines.
     * By default, the value is calculated. It is recommended not to modify it.
     *
     * @param startRow start row index
     * @return Writer
     */
    public FluentExcelWriter start(int startRow) {
        if (startRow < 0) {
            throw new IllegalArgumentException("start cannot be less than 0");
        }
        this.startRow = startRow;
        return this;
    }

    /**
     * Set the title of the Excel table, do not write the title without setting
     *
     * @param title excel title
     * @return Writer
     */
    public FluentExcelWriter headerTitle(String title) {
        this.headerTitle = title;
        return this;
    }

    /**
     * Sets the style of the Excel title,
     * which is modified by the default style to receive a CellStyle object
     *
     * @param titleStyle title style consumer
     * @return Writer
     */
    public FluentExcelWriter titleStyle(StyleConsumer<Workbook, CellStyle> titleStyle) {
        this.titleStyle = titleStyle;
        return this;
    }

    /**
     * Sets the style of the Excel column header,
     * which is modified by the default style to receive a CellStyle object
     *
     * @param headerStyle header style consumer
     * @return Writer
     */
    public FluentExcelWriter headerStyle(StyleConsumer<Workbook, CellStyle> headerStyle) {
        this.headerStyle = headerStyle;
        return this;
    }

    /**
     * Sets the style of the Excel row column,
     * which is modified by the default style to receive a CellStyle object
     *
     * @param cellStyle row style consumer
     * @return Writer
     */
    public FluentExcelWriter cellStyle(StyleConsumer<Workbook, CellStyle> cellStyle) {
        this.cellStyle = cellStyle;
        return this;
    }

    /**
     * Specify to write an Excel table from a template file
     *
     * @param templatePath template file path
     * @return Writer
     */
    public FluentExcelWriter withTemplate(String templatePath) {
        return this.withTemplate(new File(templatePath));
    }

    /**
     * Specify to write an Excel table from a template file
     *
     * @param template template file instance
     * @return Writer
     */
    public FluentExcelWriter withTemplate(File template) {
        if (null == template || !template.exists()) {
            throw new IllegalArgumentException("template file not exist");
        }
        this.template = template;
        return this;
    }

    /**
     * This setting is only valid for xlsx format Excel
     * <p>
     * The default buffer is 100, which can be adjusted according to the number of write lines.
     * <p>
     * If you are not sure, please do not set
     *
     * @param bufferSize
     * @return
     */
    public FluentExcelWriter bufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    public FluentExcelWriter createRow(Consumer<Sheet> sheetConsumer) {
        this.sheetConsumer = sheetConsumer;
        return this;
    }

    public FluentExcelWriter withRaw() {
        this.withRaw = true;
        return this;
    }

    public FluentExcelWriter charset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public FluentExcelWriter isAppend(boolean isAppend) {
        this.isAppend = isAppend;
        return this;
    }

    /**
     * Write an Excel document to a file
     *
     * @param file excel file
     * @throws WriterException
     */
    public void to(File file) throws WriterException {
        try {
            this.to(new FileOutputStream(file, isAppend));
        } catch (FileNotFoundException e) {
            throw new WriterException(e);
        }
    }

    /**
     * Write an Excel document to the output stream
     *
     * @param outputStream outputStream
     * @throws WriterException
     */
    public void to(OutputStream outputStream) throws WriterException {
        if (!withRaw && (null == rows || rows.isEmpty())) {
            throw new WriterException("write rows cannot be empty, please check it");
        }
        if (excelType == TableType.XLSX) {
            new WriterWith2007(outputStream).writeSheet(this);
        }
        if (excelType == TableType.XLS) {
            new WriterWith2003(outputStream).writeSheet(this);
        }
        if (excelType == TableType.CSV) {
            new WriterWithCSV(outputStream).writeSheet(this);
        }
    }

    public int startRow() {
        return this.startRow;
    }

    public String sheetName() {
        return this.sheetName;
    }

    public StyleConsumer<Workbook, CellStyle> titleStyle() {
        return this.titleStyle;
    }

    public StyleConsumer<Workbook, CellStyle> headerStyle() {
        return this.headerStyle;
    }

    public StyleConsumer<Workbook, CellStyle> cellStyle() {
        return this.cellStyle;
    }

    public File template() {
        return this.template;
    }

    public String headerTitle() {
        return this.headerTitle;
    }

    public int bufferSize() {
        return bufferSize;
    }

    public Collection<?> rows() {
        return rows;
    }

    public Consumer<Sheet> sheetConsumer() {
        return sheetConsumer;
    }

    public boolean isRaw() {
        return withRaw;
    }

    public Charset charset() {
        return this.charset;
    }

}
