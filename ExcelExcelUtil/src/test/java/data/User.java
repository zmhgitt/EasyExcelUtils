package data;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.zmh.easyexcel.annotation.ExcelPropertySupport;
import com.zmh.easyexcel.annotation.ExcelUri;
import lombok.Data;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/4/23 11:47
 * @description:
 */
@Data
@ColumnWidth(30)
@ExcelIgnoreUnannotated
public class User {

    @ExcelProperty(value = {"性别"})
    @ExcelPropertySupport(value = "1=男,2=女",groups = Group.Group1.class)
    private Integer sex;

    @ExcelProperty(value = "学生姓名")
    @ExcelUri(value = "点击查看",uri = "https://github.com/alibaba/easyexcel")
    private String name;

    @ExcelProperty(value = {"状态"})
    @ExcelPropertySupport(value = "1=正常,2=禁用",groups = Group.Group2.class)
    private Integer status;

//    @ExcelProperty(value = {"部门"},converter = )
//    private Dept dept;
}
