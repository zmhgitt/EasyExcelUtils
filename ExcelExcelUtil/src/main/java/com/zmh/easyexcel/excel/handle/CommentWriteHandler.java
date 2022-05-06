package com.zmh.easyexcel.excel.handle;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.zmh.easyexcel.annotation.ExcelPropertySupport;
import com.zmh.easyexcel.entity.WriteField;
import com.zmh.easyexcel.context.WriterContext;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.util.List;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/4/26 10:35
 * @description:
 *
 * 在标题增加批注
 *
 * 表格列一定是从第一列开始
 * 不支持嵌套 List Map 或 实体  EasyExcel 默认就不支持实体List嵌套，只要嵌套了，必须要声明对应的转换器对象
 * 不支持不定列  （此处是根据实体类field顺序来定义excel col 下标）
 */
public class CommentWriteHandler implements RowWriteHandler {


    private WriterContext context;

    public CommentWriteHandler(WriterContext context){
        this.context = context;
    }

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {
        if (aBoolean){ //批注只对标题有效
            List<WriteField> writeFields = context.getWriteFields();
            if (writeFields.size() < 1){
                return;
            }
            Sheet sheet = writeSheetHolder.getSheet();
            Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
            for (WriteField writeField : writeFields){
                //因为可能有多层标题，批注只在最后一层标题设置，此处判断层级是否是最后一层
                if (writeField.getLastTitleIndex() != (integer+1)){
                    //不符合最后一层，不加批注
                    continue;
                }
                ExcelPropertySupport excelPropertySupport = writeField.getExcelPropertySupport();
                if (excelPropertySupport != null && !"".equals(excelPropertySupport.tips())){
                    //增加批注操作
                    Comment comment =
                            drawingPatriarch.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, writeField.getIndex(), integer, writeField.getIndex(), integer+1));
                    // 输入批注信息
                    comment.setString(new XSSFRichTextString(excelPropertySupport.tips()));
                }
            }
        }
    }
}
