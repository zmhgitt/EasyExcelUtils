package data.write.poi;

import com.zmh.poi.entity.CellData;
import com.zmh.poi.util.CellDataUtils;
import com.zmh.poi.writer.ExcelWriter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/6 16:53
 * @description:
 */
@Ignore
public class PoiWriterText {

    @Test
    public void write() throws FileNotFoundException {
        //1、创建ExcelWriter对象  2007
        ExcelWriter excelWriter = new ExcelWriter();
        //2、获取输出流，web可以使用Response.getOutputStream()，此处用暂时随便
        OutputStream outputStream = new FileOutputStream(new File("D:\\11\\" + System.currentTimeMillis() + ".xlsx"));

        //3、添加行，
        //该工具类行通过一行一行进行写入，每一行都是CellData的list对象，
        //{@link CellData}
        //可以将导出对象转化为List<CellData> 再进行导出
        //建议使用CellDataUtils#getCellData获取CellData对象
        //主要作用是自定义每一行格式，，，注意一个workbook最多4000个样式
        for (int i = 0;i<180;i++){
            excelWriter.addRow(data1());
            excelWriter.addRow(data2());
            excelWriter.addRow(data3(),2);
        }

        //4、导出
        excelWriter.setOutputStream(outputStream)
                .finish();
    }


    private List<CellData> data1(){
        List<CellData> data = new ArrayList<CellData>();
        CellDataUtils cellDataUtils = new CellDataUtils();
        for (int i=0;i<10;i++){
            CellData cellData = cellDataUtils.getCellData("column" + i);
            //设置边框
            cellData.setHasBorder(true);
            //设置字体居中
            cellData.setFontCenter(true);
            //设置字体颜色
            cellData.setFontColor(IndexedColors.RED);
            data.add(cellData);
        }
        return data;
    }

    private List<CellData> data2(){
        List<CellData> data = new ArrayList<CellData>();
        CellDataUtils cellDataUtils = new CellDataUtils();
        for (int i=0;i<2;i++){
            CellData cellData = cellDataUtils.getCellData("column" + i,0,4);
            //设置字体居中
            cellData.setFontCenter(true);
            //设置边框
            cellData.setHasBorder(true);
            data.add(cellData);
        }
        return data;
    }

    private List<CellData> data3(){
        List<CellData> data = new ArrayList<CellData>();
        CellDataUtils cellDataUtils = new CellDataUtils();
        for (int i=0;i<10;i++){
            CellData cellData = cellDataUtils.getCellData("column" + i,2,0);
            //设置字体居中
            cellData.setFontCenter(true);
            //设置边框
            cellData.setHasBorder(true);
            data.add(cellData);
        }
        return data;
    }
}
