package com.figure.core.util.copycat.annotaion;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.figure.core.util.copycat.enums.ColumnNamingStrategy;

import java.lang.annotation.*;

/**
 * 模糊查询(LIKE) 条件注解
 *
 * @author feather
 * @date 2021-04-06
 * @see {@link com.figure.core.util.copycat.processor.LikeProcessor}
 */
@Documented
@CriteriaQuery
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Like {

    /**
     * 自定义的属性值
     */
    String alias() default "";

    /**
     * 匹配模式
     */
    SqlLike like() default SqlLike.DEFAULT;

    /**
     * 默认下划线
     *
     * @return ColumnNamingStrategy
     */
    ColumnNamingStrategy naming() default ColumnNamingStrategy.FIRST_LOWER_CASE_CAMEL;

}
