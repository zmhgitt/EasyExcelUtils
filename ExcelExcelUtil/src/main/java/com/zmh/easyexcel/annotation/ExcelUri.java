package com.zmh.easyexcel.annotation;

import java.lang.annotation.*;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/5/11 16:31
 * @description:
 *
 * 只能声明在String上不然会报错
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Deprecated
public @interface ExcelUri {

    /**
     * URL的值
     * */
    String value()  default "";

    /**
     * uri 前缀
     * */
    String uri()  default "";

    /**
     * 是否带其本身的值，带则在后面追加其值，不带则直接访问uri
     * */
    boolean onlyUri() default true;
}
