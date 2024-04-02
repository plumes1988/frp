package com.figure.op.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 绩效模板明细等级枚举类
 *
 * @author ly
 */
@Getter
@AllArgsConstructor
public enum PerformanceTemplateLevelEnum {

    FIRST("first", "一级"),

    SECOND("second", "二级");

    /**
     * 层级
     */
    private final String level;
    /**
     * 层级名称
     */
    private final String name;

}
