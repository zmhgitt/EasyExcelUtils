package com.zmh.poi.writer;

import com.zmh.poi.builder.ExcelWriterBuilder;
import com.zmh.poi.entity.CellData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.util.List;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/6 15:01
 * @description:
 *
 * 导出
 */
public class ExcelWriter {

    private ExcelWriterBuilder excelWriterBuilder;

    public ExcelWriter() {
        //2007xssf
        this.excelWriterBuilder = new ExcelWriterBuilder(new HSSFWorkbook());
    }

    public ExcelWriter setOutputStream(OutputStream outputStream){
        excelWriterBuilder.setOutputStream(outputStream);
        return this;
    }

    /**
     * add row
     * @param cellDataList 一行所有列数据
     * */
    public void addRow(List<CellData> cellDataList){
        excelWriterBuilder.addRow(cellDataList);
    }

    /**
     * add row
     * @param cellDataList 一行所有列数据
     * @param skipRow 往后跳过几行 不能为负数，不要想着传负数就往前跳😏
     * */
    public void addRow(List<CellData> cellDataList,int skipRow){
        excelWriterBuilder.addRow(cellDataList,skipRow);
    }

    public void finish(){
        excelWriterBuilder.finish();
    }
}
