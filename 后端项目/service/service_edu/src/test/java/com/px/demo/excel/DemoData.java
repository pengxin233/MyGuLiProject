package com.px.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {

    //设置Excel表头
    @ExcelProperty(value = "学生编号",index = 0)
    private String sno;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
