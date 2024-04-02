package com.figure.core.util.page;

import com.figure.core.constant.PageConstants;
import com.figure.core.util.ServletUtils;

/**
 * 表格数据处理
 *
 * @author feather
 */
public class TableSupport {

    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(PageConstants.PAGE_NUM_FIELD));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PageConstants.PAGE_SIZE_FIELD));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(PageConstants.ORDER_BY_COLUMN_FIELD));
        pageDomain.setIsAsc(ServletUtils.getParameter(PageConstants.IS_ASC_FIELD));
        return pageDomain;
    }

    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }

}
