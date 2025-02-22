package com.figure.core.util.copycat.processor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.util.copycat.annotaion.ConditionProcessor;
import com.figure.core.util.copycat.annotaion.Timestamp;
import com.figure.core.util.copycat.enums.CompareEnum;
import com.figure.core.util.copycat.query.AbstractQuery;
import com.figure.core.util.copycat.util.TimeUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * {@link Timestamp} 注解处理器
 *
 * @param <QUERY>  自定义查询 Query
 * @param <ENTITY> 查询想对应的实体类型
 * @author feather
 * @date 2021-04-06
 */
@ConditionProcessor(targetAnnotation = Timestamp.class)
public class TimestampProcessor<QUERY extends AbstractQuery, ENTITY>
        extends CriteriaAnnotationProcessorAdaptor<Timestamp, QUERY, QueryWrapper<ENTITY>, ENTITY> {

    @Override
    public boolean process(QueryWrapper<ENTITY> queryWrapper, Field field, QUERY query, Timestamp criteriaAnnotation) {

        final Object value = this.columnValue(field, query);
        if (this.isNullOrEmpty(value)) {
            // 属性值为 Null OR Empty 跳过
            return true;
        }
        String columnName = criteriaAnnotation.alias();
        if (StringUtils.isEmpty(columnName)) {
            columnName = this.columnName(field, criteriaAnnotation.naming());
        }
        assert columnName != null;
        long timeStamp = Long.valueOf(String.valueOf(value));
        CompareEnum compare = criteriaAnnotation.compare();
        // TODO 这儿考虑到有些是 JDK 7 的场景,所以默认采用 {@link java.util.Date}
        // TODO 如果是JDK 8 及以上的话 可以考虑采用 {@link java.time.LocalDateTime}
        Class<?> clazz = criteriaAnnotation.clazz();
        switch (compare) {
            case EQ:
                // queryWrapper.eq(null != value, columnName, TimeUtils.toTime(timeStamp, clazz));
                // @since 1.1.0
                queryWrapper.eq(null != value, columnName, TimeUtils.toTime(timeStamp, clazz));
                break;
            case NE:
                queryWrapper.ne(null != value, columnName, TimeUtils.toTime(timeStamp, clazz));
                break;
            case GE:
                queryWrapper.ge(null != value, columnName, TimeUtils.toTime(timeStamp, clazz));
                break;
            case GT:
                queryWrapper.gt(null != value, columnName, TimeUtils.toTime(timeStamp, clazz));
                break;
            case LE:
                queryWrapper.le(null != value, columnName, TimeUtils.toTime(timeStamp, clazz));
                break;
            default:
                queryWrapper.lt(null != value, columnName, TimeUtils.toTime(timeStamp, clazz));
                break;
        }

        return true;
    }

}
