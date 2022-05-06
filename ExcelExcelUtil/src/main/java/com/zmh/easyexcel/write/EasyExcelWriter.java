package com.zmh.easyexcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.zmh.easyexcel.annotation.ExcelPropertySupport;
import com.zmh.easyexcel.annotation.group.Default;
import com.zmh.easyexcel.builder.WriterBuilder;
import com.zmh.easyexcel.entity.PageWriter;

import java.io.*;
import java.util.List;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/4/23 11:53
 * @description:
 *
 * 不支持嵌套 List Map 或 实体  EasyExcel 默认就不支持实体List嵌套，只要嵌套了，必须要声明对应的转换器对象
 * 不支持继承
 *
 * 工具比较简单，所以整个工具没有任何接口类，当然也没必要
 */
public class EasyExcelWriter<T> {


    private WriterBuilder writerBuilder;

    public EasyExcelWriter(Class clazz) {
        this.writerBuilder = new WriterBuilder(clazz);
    }

    /**
     * 最简单的导出
     *
     * 当数据量大时，需要极大的内存，为同步导出
     *
     * @param list 导出对象list
     * */
    public void simpleExport(List<T> list){
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            excelWriter = writerBuilder.createExcelWriter(null).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.write(list, writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 分页导出
     *
     * 判断条件 list.size() < length 即结束
     * @param list 导出对象list
     *
     * */
    public void pageExport(List<T> list, Integer length, PageWriter pageWriter){
        if (pageWriter.getExcelWriter() == null){
            pageWriter.setExcelWriter(writerBuilder.createExcelWriter(pageWriter.getOutputStream()).build());
        }
        pageWriter.write(list,length);
    }

    /**
     * 设置Outputstream,实际上调用了createExcelWriter方法
     * */
    public EasyExcelWriter<T> setOutputStream(OutputStream outputStream){
        writerBuilder.createExcelWriter(outputStream);
        return this;
    }
    /**
     *
     *  通过 {@link ExcelPropertySupport#groups()}
     *  确定目前需要导出的参数
     *  该功能是可以让用户在一个实体类这实现不同的导出参数，无需创建第二个实体类
     *  不过建议不要弄到太多太混就行，
     *  如果想同时导出那些没有手动声明groups()的，请另class继承{@link Default}
     * */
    public EasyExcelWriter<T> group(Class groups){
        if (writerBuilder.getContext() == null){
            throw new RuntimeException("EasyExcelWriter.createExcelWriter get ExcelWriter");
        }
        writerBuilder.getContext().doGroups(groups);
        writerBuilder.includeProperty(writerBuilder.getContext().getFieldNameSet());
        return this;
    }
}
