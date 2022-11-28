package com.tz.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestExcel {
    public static void main(String[] args) {
        String fileName="/Users/zhaojin/Downloads/test.xlsx";
//        写操作
//        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(getData());
//        读操作
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }
    private static List<DemoData> getData(){
        List<DemoData> demoDataList =new ArrayList<>();
        for (int i=0; i<10 ; i++){
            DemoData demoData = new DemoData();
            demoData.setSn(i);
            demoData.setSname("lusy"+i);
            demoData.setSclass(i+"班级");
            demoDataList.add(demoData);
        }
        return demoDataList;
    }
}
