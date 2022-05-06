package com.zmh.easyexcel.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.zmh.easyexcel.annotation.ExcelPropertySupport;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/5 18:34
 * @description:
 */
public class StringPropertyConverter  implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String str = cellData.getStringValue();
        //判断是否有该注解
        ExcelPropertySupport excelPropertySupport = excelContentProperty.getField().getAnnotation(ExcelPropertySupport.class);

        //1、实现类型映射值 Value
        if (excelPropertySupport != null){
            if (str == null || "".equals(str.trim())){
                return str;
            }else{
                if (str.startsWith(excelPropertySupport.prefix())){
                    str = str.substring(excelPropertySupport.prefix().length()+1);
                }
                if (str.endsWith(excelPropertySupport.suffix())){
                    str = str.substring(0,str.length()-excelPropertySupport.prefix().length());
                }
            }
        }
        return str;
    }

    @Override
    public CellData convertToExcelData(String str, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //判断是否有该注解
        ExcelPropertySupport excelPropertySupport = excelContentProperty.getField().getAnnotation(ExcelPropertySupport.class);

        //1、实现类型映射值 Value
        if (excelPropertySupport != null){
            if (str == null){
                return new CellData(str);
            }else{
                return new CellData(excelPropertySupport.prefix()+str+excelPropertySupport.suffix());
            }
        }
        return new CellData(str);
    }
}
