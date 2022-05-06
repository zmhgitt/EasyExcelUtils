package com.zmh.easyexcel.annotation;

import com.zmh.easyexcel.annotation.group.Default;
import com.zmh.easyexcel.enums.ExcelType;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelPropertySupport {

    /**
     * 1=男,2=女
     * */
    String value()  default "";

    /**
     * 批注提示，只在标题提示，多层标题时，只在最后一层提示
     * 只支持导出
     * 第一列不为表格数据勿用此属性，不然会导致属性和批注对不上
     *
     * 不设置初始列，因为EasyExcel并没有初始列设置
     * */
    String tips() default "";

    /**
     * 分组  主要应对一个实体类  需要不同的导出方式，只对type 为  EXPORT  或  ALL 生效
     * */
    Class<?>[] groups() default Default.class;


    /**
     *  前缀，该注解只针对String 类型
     *  作用：比如图片路径前面需要加:https//:ip:port
     * */
    String prefix() default "";

    /**
     *  后缀，该注解只针对String 类型
     *  作用：比如图片路径前面需要加:https//:ip:port
     * */
    String suffix() default "";

    /**
     * export | import  | all
     * */
    ExcelType type() default ExcelType.ALL;
}
