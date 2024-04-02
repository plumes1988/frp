package com.figure.core.util.copycat.annotaion;

import com.figure.core.util.copycat.enums.ColumnNamingStrategy;
import com.figure.core.util.copycat.enums.CompareEnum;

import java.lang.annotation.*;
import java.util.Date;

/**
 * 时间戳 条件注解
 * 使用场景:
 * 有些时候前端是将时间戳传入后台 这个时候需要自动将时间戳转换为时间对象
 * Long -> Date
 *
 * @author feather
 * @date 2021-04-06
 */
@Documented
@CriteriaQuery
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Timestamp {

    /**
     * 自定义的属性值
     */
    String alias() default "";

    /**
     * 比较运行符
     */
    CompareEnum compare() default CompareEnum.EQ;

    /**
     * 转换的时间对象
     * 默认采用 {@link Date}
     */
    Class<?> clazz() default Date.class;

    /**
     * 默认下划线
     *
     * @return ColumnNamingStrategy
     */
    ColumnNamingStrategy naming() default ColumnNamingStrategy.FIRST_LOWER_CASE_CAMEL;

}
