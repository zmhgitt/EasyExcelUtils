package com.zmh.poi.builder;

import com.zmh.poi.entity.CellData;
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
 *
 * 本方法采用实时写入的方式进行导出
 * addRow调用一次便使用一次，并且不会收集所有的row
 */
public class ExcelWriterBuilder {

    private Workbook workbook;

    private Sheet curSheet = null; //可以创建List记录Sheet，我这里就不创建了，用不上

    private int sheetNo = 1;
    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;
    /**
     * Excel sheet最大style数，默认4000
     */
    public static final int styleSize = 4000;

    //当前行
    private int curRow = 0;
    private int totalRow = 0;

    private int curStyleNum = 0;

    private OutputStream outputStream;

    public ExcelWriterBuilder(Workbook workbook) {
        this.workbook = workbook;
    }

    /**
     * add row
     * 实时添加数据，实则可以写一个list添加用户数据，然后写一个dowrite再实现真实添加
     * */
    public void addRow(List<CellData> cellDataList){
        if (cellDataList == null){
            return;
        }
        Row row = createRow();
        for (CellData cellData : cellDataList){
            CellDataBuilder.newInstance(cellData,workbook,curSheet,row).doCell();
        }
    }

    /**
     * add row
     * @param cellDataList 一行所有列数据
     * @param skipRow 往后跳过几行
     * */
    public void addRow(List<CellData> cellDataList,int skipRow){
        if (cellDataList == null){
            return;
        }
        Row row = createRow();
        if (skipRow > 0){
            this.curRow += skipRow;
        }
        for (CellData cellData : cellDataList){
            CellDataBuilder
                    .newInstance(cellData, workbook, curSheet, row)
                    .doCell();
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
