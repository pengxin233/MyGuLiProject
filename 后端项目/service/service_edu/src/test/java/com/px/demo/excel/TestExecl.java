package com.px.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestExecl {
    public static void main(String[] args) {
//        write();
        read();
    }

    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i+"");
            demoData.setSname("px"+i);
            list.add(demoData);
        }
        return list;
    }

    private static void write(){
        //实现Excel的写操作

        String filename = "E://execltest.xlsx";

        //filename为路径名称,第二个参数为实体类class
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());
    }

    private static void read(){
        //读取的文件路径
        String filename = "E://execltest.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }
}
