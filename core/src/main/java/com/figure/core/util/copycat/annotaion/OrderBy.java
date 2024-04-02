package com.figure.core.util.copycat.annotaion;

import com.figure.core.util.copycat.enums.ColumnNamingStrategy;
import com.figure.core.util.copycat.enums.HandleOrderByEnum;
import com.figure.core.util.copycat.enums.OrderByEnum;

import java.lang.annotation.*;

/**
 * 排序(ORDER BY) 条件注解
 *
 * @author feather
 * @date 2021-04-06
 * @see {@link com.figure.core.util.copycat.processor.OrderByProcessor}
 */
@Documented
@CriteriaQuery
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderBy {

    /**
     * 自定义的属性值
     */
    String alias() default "";

    /**
     * 排序
     * 说明: 由于解析器在解析注解的
     *
     * @return {@link OrderByEnum}
     */
    OrderByEnum orderBy() default OrderByEnum.ASC;

    /**
     * 默认下划线
     *
     * @return {@link ColumnNamingStrategy}
     */
    ColumnNamingStrategy naming() default ColumnNamingStrategy.FIRST_LOWER_CASE_CAMEL;

    /**
     * 排序字段的类型
     * STATIC: 静态排序 - 只要包含了 {@link OrderBy} 注解的字段均参与排序
     * DYNAMIC: 动态排序 - 包含了 {@link OrderBy} 注解的字段 且前端传了值的字段将参与排序
     *
     * @return {@link HandleOrderByEnum}
     */
    HandleOrderByEnum handleType() default HandleOrderByEnum.STATIC;

}
