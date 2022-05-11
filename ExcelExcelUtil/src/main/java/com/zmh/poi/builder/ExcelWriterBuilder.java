package com.zmh.poi.builder;

import com.zmh.poi.entity.CellData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

    private CellDataBuilder cellDataBuilder;

    private Workbook workbook;

    private Sheet curSheet;

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

    private int maxColumn = 0;



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
        if (cellDataList.size() > maxColumn){
            maxColumn = cellDataList.size();
        }

        Row row = createRow(getMaxMergeRow(cellDataList));
        for (CellData cellData : cellDataList){
            cellDataBuilder.doCell(row,cellData);
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
        if (cellDataList.size() > maxColumn){
            maxColumn = cellDataList.size();
        }
        Row row = createRow(getMaxMergeRow(cellDataList));
        if (skipRow > 0){
            this.curRow += skipRow;
        }
        for (CellData cellData : cellDataList){
            cellDataBuilder.doCell(row,cellData);
        }
    }

    /**设置列宽度*/
    public void setColumnWidth(int width){
        for (int i=0; i<maxColumn;i++){
            this.setColumnWidth(i,width);
        }
    }
    public void setColumnWidth(int colIndex,int width){
        if (colIndex < 0 || width < 0){
            throw new RuntimeException();
        }
        int sheetNum = workbook.getNumberOfSheets();
        for (int i=0;i<sheetNum;i++){
            workbook.getSheetAt(i).setColumnWidth(colIndex,width*256);
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

    private Row createRow(int maxMergeRow){
        if (curSheet == null){
            this.curSheet = createSheet("sheet"+sheetNo++);
            this.cellDataBuilder = new CellDataBuilder(workbook,curSheet);
        }
        totalRow++;
        if ((curRow+maxMergeRow) < sheetSize){
            return curSheet.createRow(curRow++);
        }else{
            this.curSheet = createSheet("sheet"+sheetNo++);
            this.cellDataBuilder = new CellDataBuilder(workbook,curSheet);
            curRow = 0;
            return curSheet.createRow(curRow++);
        }
    }

    private int getMaxMergeRow(List<CellData> cellDataList){
        int maxMergeRow = 0;
        for (CellData cellData : cellDataList){
            if (cellData.getMergeRowNum() != null && cellData.getMergeRowNum() > maxMergeRow){
                maxMergeRow = cellData.getMergeRowNum();
            }
        }
        return maxMergeRow;
    }
}
