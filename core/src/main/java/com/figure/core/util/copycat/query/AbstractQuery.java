package com.figure.core.util.copycat.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.figure.core.util.copycat.advisor.CriteriaAnnotationProcessorAdvisor;
import com.figure.core.util.page.PageDomain;
import com.figure.core.util.StringUtils;

/**
 * <p>
 * mybatis-plus抽象查询对象
 * </p>
 *
 * @author feather
 * @date 2020-10-29 9:04
 */
public class AbstractQuery<T> extends PageDomain {

    /**
     * <p>
     * 自动包装查询 QueryWrapper
     * </p>
     *
     * @return QueryWrapper
     */
    public QueryWrapper<T> autoWrapper() {
        //创建指定类型的条件构造器
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        // ORDER_BY_COLUMN不为空时执行排序
        if (StringUtils.isNotEmpty(getOrderByColumn())) {
            // condition: 是否执行排序, isAsc: 排序方向（asc 正序 、 desc 倒序）
            queryWrapper.orderBy(true, "asc".equals(getIsAsc()), columns());
        }
        return CriteriaAnnotationProcessorAdvisor.advise(this, queryWrapper);
    }

    /**
     * <p>
     * 自动包装分页查询 QueryWrapper
     * 为空或其他字符时不进行分页，方便查询所有
     * </p>
     *
     * @return QueryWrapper
     */
    public QueryWrapper<T> autoPageWrapper() {
        //设置初始页数和页容量
        if (!StringUtils.isNull(this.getPageNum()) && !StringUtils.isNull(this.getPageSize())) {
            PageHelper.startPage(this.getPageNum(), this.getPageSize());
        }
        return autoWrapper();
    }

    /**
     * 构建Pagination分页对象
     *
     * @return IPage
     */
    @Deprecated
    public IPage<T> createPage() {
        return new Page<>(this.getPageNum(), this.getPageSize());
    }

}