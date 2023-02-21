package io.fluentqa.table.excel.converter;


import io.fluentqa.table.base.StringUtil;
import org.apache.logging.log4j.util.Strings;

public abstract class NumberConverter  {

    String replaceComma(String value) {
        if (StringUtil.isEmpty(value)) {
            return Strings.EMPTY;
        }
        value = value.replaceAll(",", "");
        if(value.endsWith(".0")){
            return value.substring(0, value.length() - 2);
        }
        return value;
    }

}
