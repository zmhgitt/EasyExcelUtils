package data.write.poi;

import com.zmh.poi.entity.CellData;
import com.zmh.poi.writer.ExcelWrite;
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
        OutputStream outputStream = new FileOutputStream(new File("D:\\11\\" + System.currentTimeMillis() + ".xlsx"));

        ExcelWrite excelWrite = new ExcelWrite();
        excelWrite.addRow(data());
        excelWrite.setOutputStream(outputStream).finish();
    }


    private List<CellData> data(){
        List<CellData> data = new ArrayList<CellData>();
        for (int i=0;i<10;i++){
            CellData cellData = new CellData();
            cellData.setColIndex(i*2);
            cellData.setMergeColNum(1);
            cellData.setMergeRowNum(0);
            cellData.setStringValue("column"+i);
            data.add(cellData);
        }
        return data;
    }
}
