package data.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.zmh.easyexcel.annotation.ExcelPropertySupport;
import com.zmh.easyexcel.annotation.group.Default;
import com.zmh.easyexcel.entity.PageWriter;
import com.zmh.easyexcel.write.EasyExcelWriter;
import data.DataUtils;
import data.Group;
import data.User;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/4/23 12:02
 * @description:
 */
@Ignore
public class WriteTest {


    /**
     * 简单导出
     */
    @Test
    public void simpleExport() throws Exception {
        EasyExcelWriter<User> writer = new EasyExcelWriter<User>(User.class);
        // web  使用  Response.getOutputStream方法
        OutputStream outputStream = new FileOutputStream(new File("D:\\11\\" + System.currentTimeMillis() + ".xlsx"));
        //DataUtils.data()模拟数据库数据
        writer.setOutputStream(outputStream)
                .simpleExport(DataUtils.data());
    }

    /**
     * 分页导出
     * simpleExport()由于一次性数据导出，如果导出数据过多，会严重占用内存
     * <p>
     * 所以提供分页导出方法，一次一次从数据库取数据导出直至没有数据
     * <p>
     * 判断条件 list.size() < length 即结束
     */
    @Test
    public void pageExport() throws Exception {
        EasyExcelWriter<User> writer = new EasyExcelWriter<User>(User.class);
        OutputStream outputStream = new FileOutputStream(new File("D:\\11\\" + System.currentTimeMillis() + ".xlsx"));
        //PageWriter  类 有两个构造方法
        //PageWriter()
        //PageWriter(WriteSheet writeSheet)
        //当 writeSheet 为 null时,则每次循环write均创建新的sheet，如需导出在一个sheet内，则使用第二种构造方法
        //下面演示第二种,将所有数据都导入一个指定sheet内
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        PageWriter pageWriter = new PageWriter(writeSheet);
        //注意不要忘记这个
        pageWriter.setOutputStream(outputStream);
        for (int i = 0; i < 5; i++) {
            //DataUtils.data() 模拟从数据库取数据
            writer.pageExport(DataUtils.data(), 10, pageWriter);
        }

        //满足list.size() < length 让他结束   8 < 10即可
        writer.pageExport(DataUtils.data(8), 10, pageWriter);
    }

    /**
     * 自定义导出 {@link ExcelPropertySupport#groups()}
     * 支持一个实体类 实现多种导出方式，通过组来导出
     * 尽量不要太多，不然的话看得头疼
     * 未声明组 则默认 @{@link Default }
     * 如需默认导出未定义（默认）组的成员，使其继承Default即可
     * */
    @Test
    public void groupExport() throws FileNotFoundException {

        //1、简单导出的分组方式
        EasyExcelWriter<User> writer = new EasyExcelWriter<User>(User.class);
        // web  使用  Response.getOutputStream方法
        OutputStream outputStream = new FileOutputStream(new File("D:\\11\\" + System.currentTimeMillis() + ".xlsx"));
        //DataUtils.data()模拟数据库数据
        writer.setOutputStream(outputStream)//需要提前设置输出流
                //分组时仅需在此调用group即可实现分组
                .group(Group.Group1.class) //Group1为导出的组，由{@link ExcelPropertySupport#groups()} 定义
                .simpleExport(DataUtils.data(1000));

        //2、分页导出的分组方式
//        //仅需事先声明其余不变
//        writer.setOutputStream(outputStream).group(Group.Group1.class);
//
//        WriteSheet writeSheet = writer.createExcelWriteSheet("sheet1");
//        PageWriter pageWriter = new PageWriter(writeSheet);
//        //注意不要忘记这个
//        pageWriter.setOutputStream(outputStream);
//        for (int i = 0; i < 5; i++) {
//            //DataUtils.data() 模拟从数据库取数据
//            writer.pageExport(DataUtils.data(), 10, pageWriter);
//        }
//
//        //满足list.size() < length 让他结束   8 < 10即可
//        writer.pageExport(DataUtils.data(8), 10, pageWriter);
    }
}
