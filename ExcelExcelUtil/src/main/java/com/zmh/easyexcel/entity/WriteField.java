package com.zmh.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zmh.easyexcel.annotation.ExcelPropertySupport;
import com.zmh.easyexcel.annotation.ExcelUri;

import java.lang.reflect.Field;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/4/28 15:51
 * @description:
 */
public class WriteField {

    private Field field;

    /**
     * field 下标
     * */
    private int index;
    /**
     * 标题最高层级
     * */
    private int lastTitleIndex = 0;

    private ExcelProperty excelProperty;

    private ExcelPropertySupport excelPropertySupport;

    private ExcelUri excelUri;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public ExcelProperty getExcelProperty() {
        return excelProperty;
    }

    public void setExcelProperty(ExcelProperty excelProperty) {
        this.excelProperty = excelProperty;
    }

    public ExcelPropertySupport getExcelPropertySupport() {
        return excelPropertySupport;
    }

    public void setExcelPropertySupport(ExcelPropertySupport excelPropertySupport) {
        this.excelPropertySupport = excelPropertySupport;
    }

    public ExcelUri getExcelUri() {
        return excelUri;
    }

    public void setExcelUri(ExcelUri excelUri) {
        this.excelUri = excelUri;
    }

    public int getLastTitleIndex() {
        return lastTitleIndex;
    }

    public void setLastTitleIndex(int lastTitleIndex) {
        this.lastTitleIndex = lastTitleIndex;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
