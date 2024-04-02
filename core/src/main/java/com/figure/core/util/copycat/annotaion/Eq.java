package com.figure.core.util.copycat.annotaion;

import com.figure.core.util.copycat.enums.ColumnNamingStrategy;

import java.lang.annotation.*;

/**
 * 等于(=) 条件注解
 *
 * @author feather
 * @date 2021-04-06
 */
@Documented
@CriteriaQuery
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Eq {

    /**
     * 自定义的属性值
     */
    String alias() default "";

    /**
     * 默认驼峰
     *
     * @return ColumnNamingStrategy
     */
    ColumnNamingStrategy naming() default ColumnNamingStrategy.FIRST_LOWER_CASE_CAMEL;

}
