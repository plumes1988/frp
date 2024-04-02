package com.figure.core.util.copycat.annotaion;

import com.figure.core.util.copycat.enums.ColumnNamingStrategy;

import java.lang.annotation.*;

/**
 * 不存在(NOT EXISTS)  条件注解
 *
 * @author feather
 * @date 2021-04-06
 */
@Documented
@CriteriaQuery
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotExists {

    /**
     * 自定义的属性值
     */
    String alias() default "";

    /**
     * TODO 还未想好
     * not exists Sql
     */
    String existsSql() default "";

    /**
     * 默认下划线
     *
     * @return ColumnNamingStrategy
     */
    ColumnNamingStrategy naming() default ColumnNamingStrategy.FIRST_LOWER_CASE_CAMEL;

}
