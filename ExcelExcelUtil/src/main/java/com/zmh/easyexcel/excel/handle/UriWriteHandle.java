package com.zmh.easyexcel.excel.handle;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.zmh.easyexcel.annotation.ExcelUri;
import com.zmh.easyexcel.context.WriterContext;
import com.zmh.easyexcel.entity.WriteField;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/11 16:39
 * @description:
 * todo: 无法对所有行进行超链接设置，目前只能拿到后500行,头疼
 */
@Deprecated
public class UriWriteHandle implements WorkbookWriteHandler {

    private WriterContext context;

    public UriWriteHandle(WriterContext writerContext){
        this.context = writerContext;
    }

    @Override
    public void beforeWorkbookCreate() {

    }

    @Override
    public void afterWorkbookCreate(WriteWorkbookHolder writeWorkbookHolder) {

    }

    @Override
    public void afterWorkbookDispose(WriteWorkbookHolder writeWorkbookHolder) {

        //1、判断是否包含ExcelUri
        List<WriteField> writeFields = context.getWriteFields();

        List<Integer> indexList = new ArrayList<Integer>(writeFields.size());
        for (int i = 0; i < writeFields.size();i++){
            if (writeFields.get(i).getExcelUri() != null){
                indexList.add(i);
            }
        }
        if (indexList.size() < 1){
            return;
        }

        //2、创建超链接
        Map<Integer, WriteSheetHolder> sheets = writeWorkbookHolder.getHasBeenInitializedSheet();
        for (Integer key : sheets.keySet()){
            WriteSheetHolder writeSheetHolder = sheets.get(key);

            Sheet sheet = writeSheetHolder.getSheet();

            for (Integer colIndex : indexList){
                WriteField writeField = writeFields.get(colIndex);
                int rowIndex = writeSheetHolder.getNewRowIndexAndStartDoWrite()-1;//直接context得到也可以
                ExcelUri excelUri = writeField.getExcelUri();
                while (sheet.getRow(rowIndex) != null && rowIndex >= writeField.getLastTitleIndex()){ //只会拿后500行
                    Row row = sheet.getRow(rowIndex--);
                    if (row.getCell(colIndex) != null){
                        Cell cell = row.getCell(colIndex);
                        CreationHelper createHelper = writeSheetHolder.getSheet().getWorkbook().getCreationHelper();
                        Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.URL);
                        //单元格必须是String类型
                        if (excelUri.onlyUri() || cell.getStringCellValue() == null){
                            hyperlink.setAddress(excelUri.uri());
                        }else{
                            hyperlink.setAddress(excelUri.uri()+cell.getStringCellValue());
                        }
                        cell.setHyperlink(hyperlink);
                        //设置值让样式生效
                        if (!"".equals(excelUri.value())){
                            cell.setCellValue(excelUri.value());
                        }else{
                            cell.setCellValue(cell.getStringCellValue());
                        }
                    }
                }
            }

        }
    }
}
