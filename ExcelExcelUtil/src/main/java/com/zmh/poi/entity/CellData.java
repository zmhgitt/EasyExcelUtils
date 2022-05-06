package com.zmh.poi.entity;

import com.zmh.poi.util.CellDataUtils;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/6 15:04
 * @description:
 *
 * Cell 对象，简单列举一些属性
 * 新增 功能时 在{@link CellDataUtils#doCell()}中增加方法实现，然后doCell方法调用一下
 */
public class CellData {

    private String stringValue;

    /**列标*/
    private Integer colIndex;

    /** 往后合并 */
    private Integer mergeRowNum;
    /** 往后合并 */
    private Integer mergeColNum;

    //###########style##############


    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Integer getColIndex() {
        return colIndex;
    }

    public void setColIndex(Integer colIndex) {
        this.colIndex = colIndex;
    }

    public Integer getMergeRowNum() {
        return mergeRowNum;
    }

    public void setMergeRowNum(Integer mergeRowNum) {
        this.mergeRowNum = mergeRowNum;
    }

    public Integer getMergeColNum() {
        return mergeColNum;
    }

    public void setMergeColNum(Integer mergeColNum) {
        this.mergeColNum = mergeColNum;
    }
}
