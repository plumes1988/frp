package com.figure.core.constant;

/**
 * <p>
 * 分页常量
 * </p>
 *
 * @author feather
 * @date 2021-04-06
 */
public class PageConstants {

    /** 分页起始页字段 */
    public static final String PAGE_NUM_FIELD = "pageNum";
    /** 分页默认起始页 */
    public static final Integer DEFAULT_PAGE_NUM = 1;

    /** 分页每页条数字段 */
    public static final String PAGE_SIZE_FIELD = "pageSize";
    /** 分页默认每页条数 */
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 排序列字段
     */
    public static final String ORDER_BY_COLUMN_FIELD = "orderByColumn";
    /**
     * 默认排序列
     */
    public static final String DEFAULT_ORDER_BY_COLUMN = "createTime";

    /** 排序的方向字段 */
    public static final String IS_ASC_FIELD = "isAsc";
    /** 默认排序的方向 "desc" 或者 "asc" */
    public static final String DEFAULT_IS_ASC = "desc";

}
