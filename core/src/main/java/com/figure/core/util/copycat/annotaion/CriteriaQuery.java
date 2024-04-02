package com.figure.core.util.copycat.annotaion;

import java.lang.annotation.*;

/**
 * 用于条件注解上面
 *
 * @author feather
 * @date 2021-04-06
 */
@Inherited
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CriteriaQuery {
}
