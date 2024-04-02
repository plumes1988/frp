package com.figure.op.performance.domain.vo.temp;

import lombok.Data;

import java.util.Date;

/**
 * 绩效模板对象 performance_template
 *
 * @date 2023-08-25
 */
@Data
public class PerformanceTemplateVo {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板说明
     */
    private String info;

    /**
     * 绩效评价评分
     */
    private Long score;

    /**
     * 创建人员ID
     */
    private Integer createUserId;

    /**
     * 创建人员名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人员ID
     */
    private Integer updateUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

}
