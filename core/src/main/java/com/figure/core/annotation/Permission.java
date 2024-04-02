package com.figure.core.annotation;

import java.lang.annotation.*;

@Documented
// 作用到方法上
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Permission {
    /**
     * 主键类型
     * {@link PermissionType}
     */
    PermissionType type() default PermissionType.VIEW;
}
