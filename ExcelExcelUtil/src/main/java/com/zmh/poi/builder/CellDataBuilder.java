package com.zmh.poi.builder;

import com.zmh.poi.entity.CellData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/6 15:43
 * @description: 专门处理 CellData 的Util类
 * 如果涉及到CellData的改动，不需要关注其它类
 * 在这里进行实现
 */
public class CellDataBuilder {

    private final CellData cellData;

    private Workbook workbook;
    private final Sheet sheet;
    private final Row row;
    private final Cell cell;

    public static CellDataBuilder newInstance(CellData cellData, Workbook workbook, Sheet sheet, Row row) {
        //没有判断null
        return new CellDataBuilder(cellData, workbook, sheet, row);
    }

    private CellDataBuilder(CellData cellData, Workbook workbook, Sheet sheet, Row row) {
        this.cellData = cellData;
        this.workbook = workbook;
        this.sheet = sheet;
        this.row = row;

        if (cellData.getDoubleValue() != null){
            cell = row.createCell(cellData.getColIndex(), CellType.NUMERIC);
        }else{
            cell = row.createCell(cellData.getColIndex(), CellType.STRING);
        }

    }

    /**
     * 所有新增方法都用此方法进行调用
     */
    public CellDataBuilder doCell() {
        setStyle(cell);
        doMerge();
        if (cellData.getDoubleValue() != null){
            cell.setCellValue(cellData.getDoubleValue());
        }else{
            cell.setCellValue(cellData.getStringValue());
        }
        return this;
    }

    /**
     * 专门为CellData服务方法
     * 此处采用实时合并，
     * 可以改为用户添加完数据在合并
     */
    private void doMerge() {
        if (mergeRow() || mergeCol()) {
            CellRangeAddress region = new CellRangeAddress(row.getRowNum(), row.getRowNum() + cellData.getMergeRowNum(), cellData.getColIndex(), cellData.getColIndex() + cellData.getMergeColNum());
            sheet.addMergedRegion(region);
            if (cellData.getBorder()) {
                RegionUtil.setBorderBottom(BorderStyle.THIN,region,sheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN,region,sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN,region,sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN,region,sheet);
            }
        }
    }

    private Boolean mergeRow() {
        if (cellData.getMergeRowNum() != null) {
            if (cellData.getMergeRowNum() > 0){
                return true;
            }
        }else{
            cellData.setMergeRowNum(0);
        }
        return false;
    }

    private Boolean mergeCol() {
        if (cellData.getMergeColNum() != null) {
            if (cellData.getMergeColNum() > 0){
                return true;
            }
        }else{
            cellData.setMergeColNum(0);
        }
        return false;
    }

    /**
     * 专门为CellData服务方法
     * 设置边框
     */
    private void setStyle(Cell cell) {
        boolean f = false;
        if (cellData.getBorder() || cellData.getFontCenter() || cellData.getFontColor() != null){
            f = true;
        }
        if (f){
            //样式对象不能超过4000;
            CellStyle cellStyle = workbook.createCellStyle();
            //设置边框
            if (cellData.getBorder() && !mergeRow() && !mergeCol()) {
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
            }
            //字体居中
            if (cellData.getFontCenter()){
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            }
            //字体 颜色
            if (cellData.getFontColor() != null) {
                Font font = workbook.createFont();
                font.setColor(cellData.getFontColor().getIndex());
                cellStyle.setFont(font);
            }
            cell.setCellStyle(cellStyle);

            if (cellData.getColumnWidth() != null && cellData.getColumnWidth() > 0){
                sheet.setColumnWidth(cellData.getColIndex(),cellData.getColumnWidth()*256);
            }
        }
    }
}
