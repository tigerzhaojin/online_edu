package com.tz.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoData {
//    设置excel表头名称
    @ExcelProperty(value = "编号",index = 0)
    private Integer sn;

    @ExcelProperty(value = "姓名",index = 1)
    private String sname;

    @ExcelProperty(value = "班级",index = 2)
    private String sclass;
}
