package io.fluentqa.table.base;

import io.fluentqa.table.base.exception.ReaderException;
import lombok.experimental.UtilityClass;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
@UtilityClass
public class ExcelUtil {

    public static <T> T newInstance(Class<T> type) {
        try {
            return type.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public static Workbook create(File file) throws ReaderException {
        try {
            return WorkbookFactory.create(file);
        } catch (IOException e) {
            throw new ReaderException(e);
        }
    }

    public static Workbook create(InputStream inputStream) throws ReaderException {
        try {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw new ReaderException(e);
        }
    }

    public static String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return fileName.substring(lastIndexOf + 1);
    }

    public static boolean isXLSX(File file) {
        if (null == file || !file.exists()) {
            return false;
        }
        String ext = getFileExtension(file.getName());
        return ext.toUpperCase().equals("XLSX");
    }

    public static boolean isXLS(File file) {
        if (null == file || !file.exists()) {
            return false;
        }
        String ext = getFileExtension(file.getName());
        return ext.toUpperCase().equals("XLS");
    }

    public static boolean isCSV(File file) {
        if (null == file || !file.exists()) {
            return false;
        }
        String ext = getFileExtension(file.getName());
        return ext.toUpperCase().equals("CSV");
    }

    public static byte[] streamAsBytes(InputStream inputStream) throws IOException {
        if (null == inputStream) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Fake code simulating the copy
        // You can generally do better with nio if you need...
        // And please, unlike me, do something about the Exceptions :D
        byte[] buffer = new byte[1024];
        int    len;
        while ((len = inputStream.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        return baos.toByteArray();
    }

    public static boolean isXLSX(InputStream inputStream) {
        try {
            new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isXLS(InputStream inputStream) {
        try {
            new HSSFWorkbook(inputStream);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
