package com.zmh.easyexcel.entity;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.OutputStream;
import java.util.List;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/4/23 16:51
 * @description:
 */
public class PageWriter {


    private ExcelWriter excelWriter;

    /** WriteSheet  为null 时则每次创建新sheet 不为null则为单一sheet*/
    private WriteSheet writeSheet;

    /**OutPutStream*/
    private OutputStream outputStream;

    /** 多sheet时 sheet名下标*/
    private int sheetSize = 0;


    public PageWriter(WriteSheet writeSheet) {
        this.writeSheet = writeSheet;
    }

    public void write(List<?> list,Integer length){
        try {
            if (this.writeSheet == null){
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetSize,"sheet"+(sheetSize++)).build();
                excelWriter.write(list, writeSheet);
            }else{
                excelWriter.write(list, this.writeSheet);
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null && list.size() < length) {
                excelWriter.finish();
            }
        }
    }

    public int getSheetSize() {
        return sheetSize;
    }

    public ExcelWriter getExcelWriter() {
        return excelWriter;
    }

    public void setExcelWriter(ExcelWriter excelWriter) {
        this.excelWriter = excelWriter;
    }

    public WriteSheet getWriteSheet() {
        return writeSheet;
    }

    public void setWriteSheet(WriteSheet writeSheet) {
        this.writeSheet = writeSheet;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
