package com.figure.core.util.copycat.advisor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.util.copycat.annotaion.*;
import com.figure.core.util.copycat.constant.QueryConstant;
import com.figure.core.util.copycat.exception.CopycatException;
import com.figure.core.util.copycat.parser.CriteriaFieldParser;
import com.figure.core.util.copycat.processor.*;
import com.figure.core.util.copycat.query.AbstractQuery;
import com.figure.core.util.copycat.util.ScanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * 注解处理器增强
 *
 * @author feather
 * @date 2021-04-06
 */
public class CriteriaAnnotationProcessorAdvisor {

    private static final Logger log = LoggerFactory.getLogger(CriteriaAnnotationProcessorAdvisor.class);

    /**
     * 条件注解处理器缓存
     */
    protected static Map<Class<? extends Annotation>, CriteriaAnnotationProcessor> ANNOTATION_PROCESSOR_CACHE = null;

    static {
        handleProcessorCacheByPackageScan();
    }

    /**
     * 从缓存中查询制定的处理器
     *
     * @param processorClazz 处理器 Class
     * @return 指定的条件注解处理器
     */
    protected static CriteriaAnnotationProcessor findProcessor(final Class<?> processorClazz) {
        //通过传入注解的类型获取对应注解的处理器对象
        final CriteriaAnnotationProcessor processor = ANNOTATION_PROCESSOR_CACHE.get(processorClazz);
        if (null == processor) {
            throw new CopycatException("No processor found:%s", processorClazz);
        }
        return processor;
    }

    /**
     * 通过条件注解完成自动包装
     * <p>
     * lambda语法
     * CriteriaFieldCallback就是一个函数式接口：他只有一个方法invoke()方法。
     * 1、因为invoke()方法有两个参数，所以   ->前面的()中就需要声明形参
     * 2、invoke返回的是boolean，所以需要return。
     * 3、->后面写的代码其实就是定义在invoke方法内的代码。如果此处代码只有一行，{}可以省略简化 。
     * 相当于 new CriteriaFieldCallback(Field field,Annotation criteriaAnnotation){
     * final CriteriaAnnotationProcessor processorCached = findProcessor(criteriaAnnotation.annotationType());
     * assert processorCached != null;
     * return processorCached.process(queryWrapper, field, query, criteriaAnnotation);
     * }
     *
     * @param query        自定义的查询对象
     * @param queryWrapper 查询包装器
     * @param <QUERY>      自定义的查询类型
     * @param <ENTITY>     实体类型
     * @return QueryWrapper
     * @see {@link QueryWrapper}
     */
    public static <QUERY extends AbstractQuery, ENTITY> QueryWrapper<ENTITY> advise(final QUERY query, QueryWrapper<ENTITY> queryWrapper) {
        CriteriaFieldParser.foreachCriteriaField(query, (field, criteriaAnnotation) -> {
            //得到对应的注解处理器
            final CriteriaAnnotationProcessor processorCached = findProcessor(criteriaAnnotation.annotationType());
            //断言处理器不为空 其实在取注解处理器时已经做过判断了，这里断言只是为了防止和确定注解处理器一定存在，一般可以用在特别重要的对象上
            assert processorCached != null;
            //调用对应注解处理器的对应process方法
            return processorCached.process(queryWrapper, field, query, criteriaAnnotation);
        });

        return queryWrapper;
    }

    // --------------------------------------------------------------------------------------- HANDLE PROCESSOR

    private static void handleProcessorCacheByPackageScan() {
        // TODO 采用包扫描的方式,替换NEW的方式, 如果失败了,再采用 NEW 的 方式
        try {
            String basePackage = QueryConstant.ANNOTATION_PROCESSOR_SCAN_BASE_PACKAGE;
            //获得处理器集合
            ANNOTATION_PROCESSOR_CACHE = ScanUtils.doScan(basePackage);
        } catch (Exception e) {
            log.error("do time processor scan exception", e);
            handleProcessorCacheByNew();
        }
    }

    private static void handleProcessorCacheByNew() {
        // TODO 根据 实际情况来分配容量
        // TODO 采用包扫描实现
        ANNOTATION_PROCESSOR_CACHE = new HashMap<>(32);

        // EQ
        ANNOTATION_PROCESSOR_CACHE.put(Eq.class, new EqProcessor<>());
        // NE
        ANNOTATION_PROCESSOR_CACHE.put(Ne.class, new NeProcessor());

        // GE
        ANNOTATION_PROCESSOR_CACHE.put(Ge.class, new GeProcessor());
        // GT
        ANNOTATION_PROCESSOR_CACHE.put(Gt.class, new GtProcessor());

        // IN
        ANNOTATION_PROCESSOR_CACHE.put(In.class, new InProcessor());
        // NOT IN
        ANNOTATION_PROCESSOR_CACHE.put(NotIn.class, new NotInProcessor());

        // IS NULL
        ANNOTATION_PROCESSOR_CACHE.put(IsNull.class, new IsNullProcessor());
        // IS NOT NULL
        ANNOTATION_PROCESSOR_CACHE.put(IsNotNull.class, new IsNotNullProcessor());

        // LE
        ANNOTATION_PROCESSOR_CACHE.put(Le.class, new LeProcessor());
        // LT
        ANNOTATION_PROCESSOR_CACHE.put(Lt.class, new LtProcessor());

        // LIKE
        ANNOTATION_PROCESSOR_CACHE.put(Like.class, new LikeProcessor());
        // NOT LIKE
        ANNOTATION_PROCESSOR_CACHE.put(NotLike.class, new NotLikeProcessor());

        // EXISTS
        ANNOTATION_PROCESSOR_CACHE.put(Exists.class, new ExistsProcessor());
        // NOT EXISTS
        ANNOTATION_PROCESSOR_CACHE.put(NotExists.class, new NotExistsProcessor());

        // GROUP BY
        ANNOTATION_PROCESSOR_CACHE.put(GroupBy.class, new GroupByProcessor());
        // HAVING
        ANNOTATION_PROCESSOR_CACHE.put(Having.class, new HavingProcessor());

        // ORDER BY
        ANNOTATION_PROCESSOR_CACHE.put(OrderBy.class, new OrderByProcessor());
        // TIMESTAMP
        ANNOTATION_PROCESSOR_CACHE.put(Timestamp.class, new TimestampProcessor());
    }

    public static void destroyProcessorCache() {
        if (null != ANNOTATION_PROCESSOR_CACHE && ANNOTATION_PROCESSOR_CACHE.size() > 0) {
            ANNOTATION_PROCESSOR_CACHE.clear();
        }
    }

}
