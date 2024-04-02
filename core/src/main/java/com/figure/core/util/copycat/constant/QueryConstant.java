package com.figure.core.util.copycat.constant;

/**
 * QueryBootConstant
 *
 * @author feather
 * @date 2020/09/19
 * @since 1.0.1
 */
public interface QueryConstant {

    /** 注解处理器的包路径 */
    String ANNOTATION_PROCESSOR_SCAN_BASE_PACKAGE = "com.figure.core.util.copycat.processor";

    /** 时间处理器的包路径 */
    String TIME_PROCESSOR_SCAN_BASE_PACKAGE = "com.figure.core.util.copycat.time";

    /** 默认扫描的问题件类型 */
    String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    /** 包分隔符: {@code '.'}. */
    char PACKAGE_SEPARATOR = '.';

    /** 路径分隔符: {@code '/'}. */
    char PATH_SEPARATOR = '/';

}
