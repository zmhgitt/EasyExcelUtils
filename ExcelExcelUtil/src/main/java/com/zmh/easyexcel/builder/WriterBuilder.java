package com.zmh.easyexcel.builder;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.zmh.easyexcel.context.WriterContext;
import com.zmh.easyexcel.excel.converter.IntegerPropertyConverter;
import com.zmh.easyexcel.excel.converter.StringPropertyConverter;
import com.zmh.easyexcel.excel.handle.CommentWriteHandler;

import java.io.OutputStream;
import java.util.Set;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/5 17:59
 * @description:
 */
public class WriterBuilder {

    /**导出实体*/
    private Class clazz;

    private WriterContext context;


    public WriterBuilder(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * 获取ExcelWriter，并初始化
     * */
    public ExcelWriterBuilder createExcelWriter(OutputStream outputStream){
        if (this.context == null){
            this.context = new WriterContext(clazz);
            this.context.initWriteField();
        }
        if (this.context.getExcelWriterBuilder() == null){
            ExcelWriterBuilder excelWriter = EasyExcel.write(outputStream, clazz)
                    .registerConverter(new IntegerPropertyConverter())
                    .registerConverter(new StringPropertyConverter())
                    .registerWriteHandler(new CommentWriteHandler(context));
            this.context.setExcelWriterBuilder(excelWriter);
            //先初始化需要导出的列
            this.includeProperty(this.context.getFieldNameSet());
            return excelWriter;
        }else{
            return this.context.getExcelWriterBuilder();
        }
    }

    /**
     * 获取ExcelWriter
     * */
    public WriteSheet createExcelWriteSheet(String sheetName){
        return EasyExcel.writerSheet(sheetName).build();
    }

    /**
     * 忽略字段
     * @param ignorePropertyName fieldName list
     * */
    public void ignoreProperty(Set<String> ignorePropertyName){
        if (this.context == null){
            throw new RuntimeException("EasyExcelWriter.createExcelWriter get ExcelWriter");
        }
        this.context.getExcelWriterBuilder().excludeColumnFiledNames(ignorePropertyName);
    }

    /**
     * 包含字段
     * @param includePropertyName fieldName list
     * */
    public void includeProperty(Set<String> includePropertyName){
        if (this.context == null){
            throw new RuntimeException("EasyExcelWriter.createExcelWriter get ExcelWriter");
        }
        this.context.getExcelWriterBuilder().includeColumnFiledNames(includePropertyName);
    }


    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public WriterContext getContext() {
        return context;
    }

    public void setContext(WriterContext context) {
        this.context = context;
    }
}
