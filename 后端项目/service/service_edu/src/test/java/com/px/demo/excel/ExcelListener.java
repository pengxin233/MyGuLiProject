package com.px.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {
//    一行一行的读取表格数据
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
//        demoData为读取的每一行数据
        System.out.println("*******"+demoData+"************");
    }

//    读取表头
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("读取表头:"+headMap);
    }

//    读取完毕后进行的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
