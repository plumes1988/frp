package com.figure.core.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用数据库映射Map数据
 *
 * @author feather
 */
public class CommonMap {

    /**
     * 状态编码转换
     */
    private static final Map<String, String> JAVA_TYPE_MAP = new HashMap<>();

    static {
        initJavaTypeMap();
    }

    /**
     * 返回状态映射
     */
    public static void initJavaTypeMap() {
        JAVA_TYPE_MAP.put("tinyint", "Integer");
        JAVA_TYPE_MAP.put("smallint", "Integer");
        JAVA_TYPE_MAP.put("mediumint", "Integer");
        JAVA_TYPE_MAP.put("int", "Integer");
        JAVA_TYPE_MAP.put("number", "Integer");
        JAVA_TYPE_MAP.put("integer", "integer");
        JAVA_TYPE_MAP.put("bigint", "Long");
        JAVA_TYPE_MAP.put("float", "Float");
        JAVA_TYPE_MAP.put("double", "Double");
        JAVA_TYPE_MAP.put("decimal", "BigDecimal");
        JAVA_TYPE_MAP.put("bit", "Boolean");
        JAVA_TYPE_MAP.put("char", "String");
        JAVA_TYPE_MAP.put("varchar", "String");
        JAVA_TYPE_MAP.put("varchar2", "String");
        JAVA_TYPE_MAP.put("tinytext", "String");
        JAVA_TYPE_MAP.put("text", "String");
        JAVA_TYPE_MAP.put("mediumtext", "String");
        JAVA_TYPE_MAP.put("longtext", "String");
        JAVA_TYPE_MAP.put("time", "Date");
        JAVA_TYPE_MAP.put("date", "Date");
        JAVA_TYPE_MAP.put("datetime", "Date");
        JAVA_TYPE_MAP.put("timestamp", "Date");
    }

}
