package io.fluentqa.table.excel;


import io.fluentqa.table.FluentExcelReader;
import io.fluentqa.table.FluentExcelWriter;
import io.fluentqa.table.base.TableType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ExcelUtilsTest {

    List<DemoExcelModel> data = Arrays.asList(new DemoExcelModel("k", 12),
            new DemoExcelModel("y", 10));

    @Test
    void testReadExcel() {
        List<DemoExcelModel> models =FluentExcelReader.create(DemoExcelModel.class,
                        new File("demo.xlsx"))
                .asList();
        System.out.println(models);

    }

    @Test
    void testWriteToExcel() {
        FluentExcelWriter.create(TableType.XLSX).withRows(
                data
        ).to(new File("demo.xlsx"));
    }
}