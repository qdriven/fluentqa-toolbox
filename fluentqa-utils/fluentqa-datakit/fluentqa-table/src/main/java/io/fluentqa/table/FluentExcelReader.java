package io.fluentqa.table;

import io.fluentqa.table.base.Result;
import io.fluentqa.table.base.StringUtil;
import io.fluentqa.table.base.exception.ReaderException;
import io.fluentqa.table.excel.reader.ReaderFactory;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FluentExcelReader<T> {

    /**
     * Java entity type to which the Excel row is mapped
     */
    private Class<T> modelType;

    /**
     * The index of the sheet to be read, default is 0
     */
    private int sheetIndex;

    /**
     * Sheet name to be read, sheet does not take effect if sheet is configured
     */
    private String sheetName;

    /**
     * Reading data from the first few lines should start with an index that really has data,
     * otherwise an error will occur. please confirm that the parameter is correct when reading.
     * <p>
     * Index starts at 0 instead of 1.
     */
    private int startRow = 1;

    /**
     * Read a excel row from a file
     */
    private File fromFile;

    /**
     * Read a excel row from a InputStream
     */
    private InputStream fromStream;

    private Charset charset = StandardCharsets.UTF_8;

    public FluentExcelReader(Class<T> modelType) {
        this.modelType = modelType;
    }

    public static <T> FluentExcelReader<T> create(Class<T> modelType) {
        return new FluentExcelReader<>(modelType);
    }

    public static <T> FluentExcelReader<T> create(Class<T> modelType, File fromFile) {
        return new FluentExcelReader<>(modelType).from(fromFile);
    }

    public static <T> FluentExcelReader<T> create(Class<T> modelType, InputStream fromStream) {
        return new FluentExcelReader<>(modelType).from(fromStream);
    }

    /**
     * Read row data from an Excel file
     *
     * @param fromFile excel file object
     * @return Reader
     */
    public FluentExcelReader<T> from(File fromFile) {
        if (null == fromFile || !fromFile.exists()) {
            throw new IllegalArgumentException("excel file must be exist");
        }
        this.fromFile = fromFile;
        return this;
    }

    /**
     * Read row data from an InputStream
     *
     * @param fromStream excel InputStream
     * @return Reader
     */
    public FluentExcelReader<T> from(InputStream fromStream) {
        this.fromStream = fromStream;
        return this;
    }

    /**
     * Set the reading from the first few lines, the index starts from 0
     *
     * @param startRow start row index
     * @return Reader
     */
    public FluentExcelReader<T> start(int startRow) {
        if (startRow < 0) {
            throw new IllegalArgumentException("start cannot be less than 0");
        }
        this.startRow = startRow;
        return this;
    }

    /**
     * The setting is read from the first sheet, the default is 0
     *
     * @param sheetIndex sheet index
     * @return Reader
     */
    public FluentExcelReader<T> sheet(int sheetIndex) {
        if (sheetIndex < 0) {
            throw new IllegalArgumentException("sheet cannot be less than 0");
        }
        this.sheetIndex = sheetIndex;
        return this;
    }

    /**
     * Set the name of the sheet to be read. If you set the name, sheet will be invalid.
     *
     * @param sheetName sheet name
     * @return
     */
    public FluentExcelReader<T> sheet(String sheetName) {
        if (StringUtil.isEmpty(sheetName)) {
            throw new IllegalArgumentException("sheet cannot be empty");
        }
        this.sheetName = sheetName;
        return this;
    }

    public FluentExcelReader<T> charset(Charset charset){
        this.charset = charset;
        return this;
    }

    /**
     * Return the read result as a Stream
     *
     * @return Stream
     * @throws ReaderException Thrown when an exception occurs during reading
     */
    public Stream<T> asStream() {
        if (modelType == null) {
            throw new IllegalArgumentException("modelType can be not null");
        }

        if (fromFile == null && fromStream == null) {
            throw new IllegalArgumentException("Excel source not is null");
        }

        if (fromFile != null) {
            return ReaderFactory.readByFile(this);
        } else {
            return ReaderFactory.readByStream(this);
        }
    }

    /**
     * Convert the read result to a List
     *
     * @return List
     * @throws ReaderException Thrown when an exception occurs during reading
     */
    public List<T> asList() throws ReaderException {
        Stream<T> stream = this.asStream();
        return stream.collect(toList());
    }

    public Result<T> asResult() throws ReaderException {
        return new Result<>(asList());
    }

    public InputStream fromStream() {
        return this.fromStream;
    }

    public File fromFile() {
        return fromFile;
    }

    public Class<T> modelType() {
        return modelType;
    }

    public int sheetIndex() {
        return this.sheetIndex;
    }

    public String sheetName() {
        return this.sheetName;
    }

    public int startRow() {
        return this.startRow;
    }

    public Charset charset(){
        return this.charset;
    }

}
