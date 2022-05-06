package com.zmh.poi.util;

import com.zmh.poi.entity.CellData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.StringUtil;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/6 15:43
 * @description:
 *
 * 专门处理 CellData 的Util类
 * 如果涉及到CellData的改动，不需要关注其它类
 * 在这里进行实现
 */
public class CellDataUtils {

    private final CellData cellData;

    private final Sheet sheet;
    private final Row row;

    private final Cell cell;
    public static CellDataUtils newInstance(CellData cellData,Sheet sheet,Row row){
        return new CellDataUtils(cellData,sheet,row);
    }

    private CellDataUtils(CellData cellData, Sheet sheet, Row row) {
        this.cellData = cellData;
        this.sheet = sheet;
        this.row = row;
        cell = row.createCell(cellData.getColIndex(), CellType.STRING);
    }

    public void doCell(){
        cell.setCellValue(cellData.getStringValue());
        mergeCol();
        mergeRow();
    }

    private void mergeRow(){
        if (cellData.getMergeRowNum() != null && cellData.getMergeRowNum() > 0){
            int startRowNo = row.getRowNum();
            int endRowNo = startRowNo+cellData.getMergeRowNum();
            //往后合并，创建需合并的row
            for (int i = startRowNo+1;i <= endRowNo;i++){
                sheet.createRow(i);//不用判断是否已经创建过，源码已经判断了
            }
            sheet.addMergedRegion(new CellRangeAddress(startRowNo, endRowNo, cellData.getColIndex(),cellData.getColIndex()));
        }
    }
    private void mergeCol(){
        if (cellData.getMergeColNum() != null && cellData.getMergeColNum() > 0){
            int startColNo = cellData.getColIndex();
            int endColNo = startColNo+cellData.getMergeColNum();
            //往后合并，创建需合并的row
            for (int i = startColNo+1;i <= endColNo;i++){
                row.createCell(i);//不用判断是否已经创建过，源码已经判断了
            }
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), startColNo,endColNo));
        }
    }
}
