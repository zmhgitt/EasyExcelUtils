package com.zmh.poi.util;

import com.zmh.poi.entity.CellData;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/6 18:43
 * @description:
 *
 * 帮助创建CellData对象
 */
public class CellDataUtils {

    private int curCol = 0;

    public CellData getCellData(String stringValue){
        return getCellData(stringValue,0,0);
    }

    public CellData getCellData(String stringValue,int mergeRowNum,int mergeColNum){
        if (mergeRowNum < 0 || mergeColNum < 0){
            throw new RuntimeException();
        }
        CellData cellData = new CellData();
        cellData.setStringValue(stringValue);
        cellData.setColIndex(curCol++);
        cellData.setMergeRowNum(mergeRowNum);
        cellData.setMergeColNum(mergeColNum);
        cellData.setHasBorder(false);
        cellData.setFontCenter(false);
        curCol += mergeColNum;
        return cellData;
    }

    public void init(){
        this.curCol = 0;
    }
}
