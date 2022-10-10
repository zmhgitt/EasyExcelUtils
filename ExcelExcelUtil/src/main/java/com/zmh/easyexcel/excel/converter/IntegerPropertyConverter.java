package com.zmh.easyexcel.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.zmh.easyexcel.annotation.ExcelPropertySupport;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/4/23 11:08
 * @description:
 */

public class IntegerPropertyConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //判断是否有该注解
        ExcelPropertySupport excelPropertySupport = excelContentProperty.getField().getAnnotation(ExcelPropertySupport.class);

        //1、实现类型映射值 Value
        if (excelPropertySupport != null){
            String[] mapStrs = excelPropertySupport.value().split(",");
            for (String map : mapStrs){
                if (map.endsWith("="+cellData.getStringValue())){
                    return Integer.valueOf(map.substring(0,map.indexOf("=")));
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public CellData convertToExcelData(Integer s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //判断是否有该注解
        ExcelPropertySupport excelPropertySupport = excelContentProperty.getField().getAnnotation(ExcelPropertySupport.class);

        //1、实现类型映射值 Value
        if (excelPropertySupport != null){
            String[] mapStrs = excelPropertySupport.value().split(",");
            for (String map : mapStrs){
                if (map.startsWith(s+"=")){
                    return new CellData(map.substring(map.indexOf("=")+1));
                }
            }
        }
        if (s != null){
            return new CellData(s+"");
        }else{
            return new CellData("");
        }

    }
}
