package io.fluentqa.table.base;

import lombok.experimental.UtilityClass;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.InputStream;

@UtilityClass
public class StringUtil {

    public static boolean isNotEmpty(String value) {
        return null != value && !value.isEmpty();
    }

    public static boolean isEmpty(String value) {
        return null == value || value.isEmpty();
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