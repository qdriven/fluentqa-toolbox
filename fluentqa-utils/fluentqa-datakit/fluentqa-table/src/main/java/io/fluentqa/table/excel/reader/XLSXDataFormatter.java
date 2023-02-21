package io.fluentqa.table.excel.reader;

import org.apache.poi.ss.usermodel.DataFormatter;

/**
 */
public class XLSXDataFormatter extends DataFormatter {

    @Override
    public String formatRawCellContents(double value, int formatIndex, String formatString, boolean use1904Windowing) {
        if ("m/d/yy".equals(formatString)) {
            formatString = "m/d/yyyy";
        }
        if ("m/d/yy h:mm".equals(formatString)) {
            formatString = "m/d/yyyy HH:mm";
        }
        String contents = super.formatRawCellContents(value, formatIndex, formatString, use1904Windowing);
        return contents;
    }

}
