package com.figure.core.util.copycat.processor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.util.copycat.annotaion.ConditionProcessor;
import com.figure.core.util.copycat.annotaion.OrderBy;
import com.figure.core.util.copycat.enums.HandleOrderByEnum;
import com.figure.core.util.copycat.enums.OrderByEnum;
import com.figure.core.util.copycat.query.AbstractQuery;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * {@link OrderBy} 注解处理器
 *
 * @param <QUERY>  自定义查询 Query
 * @param <ENTITY> 查询想对应的实体类型
 * @author feather
 * @date 2021-04-06
 */
@ConditionProcessor(targetAnnotation = OrderBy.class)
public class OrderByProcessor<QUERY extends AbstractQuery, ENTITY>
        extends CriteriaAnnotationProcessorAdaptor<OrderBy, QUERY, QueryWrapper<ENTITY>, ENTITY> {

    @Override
    public boolean process(QueryWrapper<ENTITY> queryWrapper, Field field, QUERY query, OrderBy criteriaAnnotation) {

        Object value = this.columnValue(field, query);
        HandleOrderByEnum handleType = criteriaAnnotation.handleType();
        if (HandleOrderByEnum.DYNAMIC.equals(handleType) && this.isNullOrEmpty(value)) {
            // 属性值为 Null OR Empty 跳过
            return true;
        }
        // @sine 1.1.0
        if (HandleOrderByEnum.STATIC.equals(handleType)) {
            // 保证 参与排序
            value = "1";
        }
        String columnName = criteriaAnnotation.alias();
        if (StringUtils.isEmpty(columnName)) {
            columnName = this.columnName(field, criteriaAnnotation.naming());
        }
        assert columnName != null;
        OrderByEnum orderBy = criteriaAnnotation.orderBy();
        switch (orderBy) {
            case DESC:
                queryWrapper.orderByDesc(null != value, columnName);
                break;
            default:
                queryWrapper.orderByAsc(null != value, columnName);
                break;
        }

        return true;
    }

}
