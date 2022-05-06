package com.zmh.poi.writer;

import com.zmh.poi.builder.ExcelWriterBuilder;
import com.zmh.poi.entity.CellData;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/6 15:01
 * @description:
 *
 * 导出
 */
public class ExcelWrite {

    private ExcelWriterBuilder excelWriterBuilder;

    public ExcelWrite() {
        //2007xssf
        this.excelWriterBuilder = new ExcelWriterBuilder(new XSSFWorkbook());
    }

    public ExcelWrite setOutputStream(OutputStream outputStream){
        excelWriterBuilder.setOutputStream(outputStream);
        return this;
    }

    public void addRow(List<CellData> cellDataList){
        excelWriterBuilder.addRow(cellDataList);
    }

    public void finish(){
        excelWriterBuilder.finish();
    }
}
