package com.zmh.poi.builder;

import com.zmh.poi.entity.CellData;
import com.zmh.poi.util.CellDataUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/6 15:03
 * @description:
 */
public class ExcelWriterBuilder {

    private Workbook workbook;

    private Sheet curSheet = null; //可以创建List记录Sheet，我这里就不创建了，用不上

    private int sheetNo = 1;
    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    //当前行
    private int curRow = 0;

    private int totalRow = 0;

    private OutputStream outputStream;

    public ExcelWriterBuilder(Workbook workbook) {
        this.workbook = workbook;
    }

    /**
     * add row
     * */
    public void addRow(List<CellData> cellDataList){
        if (cellDataList == null){
            return;
        }
        Row row = createRow();
        for (CellData cellData : cellDataList){
            CellDataUtils.newInstance(cellData,curSheet,row).doCell();
        }
    }

    /**输出文件   不暴露异常*/
    public void finish(){
        if (outputStream == null){
            throw new RuntimeException("outputStream is null !!!");
        }
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    private Sheet createSheet(String sheetName){
        return workbook.createSheet(sheetName);
    }

    private Row createRow(){
        if (curSheet == null){
            this.curSheet = createSheet("sheet"+sheetNo++);
        }
        totalRow++;
        if (curRow < sheetSize){
            return curSheet.createRow(curRow++);
        }else{
            this.curSheet = createSheet("sheet"+sheetNo++);
            curRow = 0;
            return curSheet.createRow(curRow++);
        }
    }
}
