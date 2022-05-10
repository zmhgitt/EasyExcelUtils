package com.zmh.poi.util;

import com.zmh.poi.entity.CellData;

import java.util.List;

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
        cellData.setBorder(false);
        cellData.setFontCenter(false);
        curCol += mergeColNum;
        return cellData;
    }

    /**
     *
     * 如果列标混乱，可以使用此方法重置列标
     * */
    public List<CellData> resetCellColumn(List<CellData> origin){
        if (origin == null || origin.size() < 1){
            return origin;
        }
        int colNum = 0;
        CellData cellData = origin.get(0);
        if (cellData != null){
            if (cellData.getColIndex()!=null){
                colNum = cellData.getColIndex();
            }else{
                cellData.setColIndex(colNum);
            }
            if (cellData.getMergeColNum() != null){
                colNum += cellData.getMergeColNum()+1;
            }
        }

        for (int i = 1; i < origin.size(); i++){
            cellData = origin.get(i);
            if (cellData.getColIndex() == null || cellData.getColIndex() != colNum){
                cellData.setColIndex(colNum);
            }
            if (cellData.getMergeColNum() != null){
                colNum += cellData.getMergeColNum()+1;
            }
        }
        return origin;
    }

    public void init(){
        this.curCol = 0;
    }
}
