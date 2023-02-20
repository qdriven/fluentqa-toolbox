package io.fluentqa.table.excel;


import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hpsf.Decimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoExcelModel {
    @ExcelColumn(title = "名称")
    private String name;
    @ExcelColumn(title = "年纪",convertToString = true)
    private Integer age;
}
