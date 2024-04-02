package com.figure.core.util.copycat.processor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.util.copycat.query.AbstractQuery;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 条件注解处理器根接口
 *
 * @param <A>      注解
 * @param <ENTITY> 包装器对应的实体类
 * @author feather
 * @date 2021-04-06
 */
public interface CriteriaAnnotationProcessor<A extends Annotation, QUERY extends AbstractQuery, WRAPPER extends QueryWrapper<ENTITY>, ENTITY> {

    /**
     * 处理注解
     *
     * @param queryWrapper       查询包装器
     * @param field              属性
     * @param query              query 对象
     * @param criteriaAnnotation 条件注解
     * @return 布尔值
     */
    boolean process(WRAPPER queryWrapper, final Field field, final QUERY query, final A criteriaAnnotation);

}
