package com.figure.core.util.copycat.processor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.util.copycat.annotaion.ConditionProcessor;
import com.figure.core.util.copycat.annotaion.IsNotNull;
import com.figure.core.util.copycat.query.AbstractQuery;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * {@link IsNotNull} 注解处理器
 *
 * @param <QUERY>  自定义查询 Query
 * @param <ENTITY> 查询想对应的实体类型
 * @author feather
 * @date 2021-04-06
 */
@ConditionProcessor(targetAnnotation = IsNotNull.class)
public class IsNotNullProcessor<QUERY extends AbstractQuery, ENTITY>
        extends CriteriaAnnotationProcessorAdaptor<IsNotNull, QUERY, QueryWrapper<ENTITY>, ENTITY> {

    @Override
    public boolean process(QueryWrapper<ENTITY> queryWrapper, Field field, QUERY query, IsNotNull criteriaAnnotation) {
        String columnName = criteriaAnnotation.alias();
        if (StringUtils.isEmpty(columnName)) {
            columnName = this.columnName(field, criteriaAnnotation.naming());
        }
        assert columnName != null;
        queryWrapper.isNotNull(columnName);

        return true;
    }

}
