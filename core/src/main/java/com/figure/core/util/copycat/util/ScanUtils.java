package com.figure.core.util.copycat.util;

import com.figure.core.util.copycat.annotaion.ConditionProcessor;
import com.figure.core.util.copycat.constant.QueryConstant;
import com.figure.core.util.copycat.processor.CriteriaAnnotationProcessor;
import com.figure.core.util.copycat.time.TimeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * 包扫描工具类
 *
 * @author feather
 * @date 2020/09/119
 * @since 1.1.0
 */
public final class ScanUtils {

    private static final Logger log = LoggerFactory.getLogger(ScanUtils.class);

    private ScanUtils() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 执行包扫描
     *
     * @param basePackages 扫描的基础包列表
     */
    public static Map<Class<? extends Annotation>, CriteriaAnnotationProcessor> doScan(String... basePackages) throws IOException {
        Map<Class<? extends Annotation>, CriteriaAnnotationProcessor> ANNOTATION_PROCESSOR_CACHE = new HashMap<>(32);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
        for (String basePackage : basePackages) {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(basePackage) + '/' + QueryConstant.DEFAULT_RESOURCE_PATTERN;
            //获取当前路径下的所有资源(可以打断点启动一下)
            Resource[] resources = pathMatchingResourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                try {
                    //对一个类的各种元数据都封装成一个MetadataReader读取器对象
                    MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
                    //得到读取器的元数据
                    ClassMetadata classMetadata = metadataReader.getClassMetadata();
                    //对元数据进行判断排序不需要的接口和抽象类
                    if (classMetadata.isInterface() || classMetadata.isAbstract()) {
                        continue;
                    }
                    //获取类的名称
                    String className = classMetadata.getClassName();
                    //通过名称取得类的对象
                    Class<?> clazz = ClassUtils.forName(className, ScanUtils.class.getClassLoader());
                    //判断该类上是否有ConditionProcessor注解
                    if (clazz.isAnnotationPresent(ConditionProcessor.class)) {
                        //取得类上的注解
                        ConditionProcessor annotation = clazz.getAnnotation(ConditionProcessor.class);
                        //调用类的默认无参构造函数创造父类对象(注：类一定要有无参构造函数否则报错)
                        CriteriaAnnotationProcessor processor = (CriteriaAnnotationProcessor) clazz.getDeclaredConstructor().newInstance();
                        //通过ConditionProcessor的targetAnnotation方法获取在类上的值
                        Class<? extends Annotation> condition = annotation.targetAnnotation();
                        ANNOTATION_PROCESSOR_CACHE.put(condition, processor);
//                        log.info("the condition annotation:[{}],and processor:[{}]",
//                                condition.getSimpleName(),
//                                processor.getClass().getSimpleName()
//                        );
                    }
                } catch (Exception e) {
                    // Ignore
                }
            }
        }

        return ANNOTATION_PROCESSOR_CACHE;
    }

    /**
     * 时间处理器扫描
     *
     * @param basePackages 扫描的基础包列表
     * @since 1.2.0
     */
    public static Map<Class<?>, TimeProcessor<?>> doTimeScan(String... basePackages) throws IOException {
        Map<Class<?>, TimeProcessor<?>> TIME_PROCESSOR_CACHE = new HashMap<>(8);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
        for (String basePackage : basePackages) {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(basePackage) + '/' + QueryConstant.DEFAULT_RESOURCE_PATTERN;
            //获取当前路径下的所有资源
            Resource[] resources = pathMatchingResourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                try {
                    //对一个类的各种元数据都封装成一个MetadataReader对象
                    MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
                    ClassMetadata classMetadata = metadataReader.getClassMetadata();
                    if (classMetadata.isInterface() || classMetadata.isAbstract()) {
                        continue;
                    }
                    String className = classMetadata.getClassName();
                    Class<?> clazz = ClassUtils.forName(className, ScanUtils.class.getClassLoader());
                    if (TimeProcessor.class.isAssignableFrom(clazz)) {
                        TimeProcessor processor = (TimeProcessor) clazz.getDeclaredConstructor().newInstance();
                        TIME_PROCESSOR_CACHE.put(clazz, processor);
                        log.info("the condition time:[{}],and processor:[{}]", clazz.getSimpleName(), processor.getClass().getSimpleName()
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Ignore
                }
            }
        }

        return TIME_PROCESSOR_CACHE;
    }

    /**
     * 取得系统路径
     *
     * @param className
     * @return
     */
    public static String convertClassNameToResourcePath(String className) {
        Assert.notNull(className, "Class name must not be null");
        //将传入的包路径转换成系统路径
        return className.replace(QueryConstant.PACKAGE_SEPARATOR, QueryConstant.PATH_SEPARATOR);
    }

    /**
     * 取得系统路径 (为什么要封装这么多次，主要是为了好看)
     *
     * @param basePackage
     * @return
     */
    public static String resolveBasePackage(String basePackage) {
        return convertClassNameToResourcePath(basePackage);
    }

}
