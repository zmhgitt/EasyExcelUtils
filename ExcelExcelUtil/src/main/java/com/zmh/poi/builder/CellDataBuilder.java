package com.zmh.poi.builder;

import com.zmh.poi.entity.CellData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/6 15:43
 * @description: 专门处理 CellData 的Util类
 * 如果涉及到CellData的改动，不需要关注其它类
 * 在这里进行实现
 */
public class CellDataBuilder {

    private CellData cellData;
    private Workbook workbook;
    private Sheet sheet;
    private Row row;
    private Cell cell;

    private Map<String, CellStyle> cellStyleMap;

    public CellDataBuilder(Workbook workbook, Sheet sheet) {

        this.workbook = workbook;
        this.sheet = sheet;
    }

    /**
     * 所有新增方法都用此方法进行调用
     */
    public CellDataBuilder doCell(Row row, CellData cellData) {
        this.row = row;
        this.cellData = cellData;

        if (cellData.getDoubleValue() != null) {
            cell = row.createCell(cellData.getColIndex(), CellType.NUMERIC);
        } else {
            cell = row.createCell(cellData.getColIndex());
        }

        setStyle(cell);
        doMerge();
        if (cellData.getDoubleValue() != null) {
            cell.setCellValue(cellData.getDoubleValue());
        } else {
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
                RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
            }
        }
    }

    private Boolean mergeRow() {
        if (cellData.getMergeRowNum() != null) {
            if (cellData.getMergeRowNum() > 0) {
                return true;
            }
        } else {
            cellData.setMergeRowNum(0);
        }
        return false;
    }

    private Boolean mergeCol() {
        if (cellData.getMergeColNum() != null) {
            if (cellData.getMergeColNum() > 0) {
                return true;
            }
        } else {
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
        if (cellData.getBorder() || cellData.getFontCenter() || cellData.getFontColor() != null) {
            f = true;
        }
        if (f) {
            if (this.cellStyleMap == null) {
                this.cellStyleMap = new HashMap<String, CellStyle>(16);
            }
            //样式对象不能超过4000;
            CellStyle cellStyle = getCellStyle();
            if (getCellStyle() != null){
                cell.setCellStyle(cellStyle);
                return;
            }

            cellStyle = workbook.createCellStyle();
            //设置边框
            if (cellData.getBorder() && !mergeRow() && !mergeCol()) {
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
            }
            //字体居中
            if (cellData.getFontCenter()) {
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
            this.setCellStyleMap(cellStyle);//缓存
        }
    }

    private void setCellStyleMap(CellStyle cellStyle){
        this.cellStyleMap.put(getCellStyleKey(),cellStyle);
    }

    private CellStyle getCellStyle(){
        String key = getCellStyleKey();
        if (key.length() < 1){
            return null;
        }
        return cellStyleMap.get(key);
    }

    private String getCellStyleKey(){
        StringBuilder key = new StringBuilder();
        if (cellData.getBorder()){
            key.append(1);
        }else{
            key.append(0);
        }
        if (cellData.getFontCenter()){
            key.append(1);
        }else{
            key.append(0);
        }
        if (cellData.getFontColor() != null){
            key.append(cellData.getFontColor().getIndex());
        }
        return key.toString();
    }
}
