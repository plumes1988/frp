package com.figure.core.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

public class PageWrapper<T> {
    Page<T> page;

    QueryWrapper<T> queryWrapper;

    Map<String,String> map;

    public PageWrapper(Page<T> page, QueryWrapper<T> queryWrapper) {
        this.page = page;
        this.queryWrapper = queryWrapper;
    }

    public PageWrapper(Page<T> page, Map<String,String> map) {
        this.page = page;
        this.map = map;
    }

    public PageWrapper() {
        super();
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public QueryWrapper<T> getQueryWrapper() {
        return queryWrapper;
    }

    public void setQueryWrapper(QueryWrapper<T> queryWrapper) {
        this.queryWrapper = queryWrapper;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
