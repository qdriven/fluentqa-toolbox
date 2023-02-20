package io.fluentqa.table.excel;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ExcelUtilsTest {

    List<DemoExcelModel> data = Arrays.asList(new DemoExcelModel("k", 12),
            new DemoExcelModel("y", 10));

    @Test
    void testReadExcel() {

        List<DemoExcelModel> models = ExcelUtils.readExcel("demo.xlsx", DemoExcelModel.class);
        System.out.println(models);

    }

    @Test
    void testWriteToExcel() {
        ExcelUtils.writeToExcel("demo.xlsx", data, DemoExcelModel.class);
    }
}