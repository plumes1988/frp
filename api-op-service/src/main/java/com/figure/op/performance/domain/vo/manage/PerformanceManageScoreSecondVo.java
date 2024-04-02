package com.figure.op.performance.domain.vo.manage;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 绩效评分-打分对象
 * @author fsn
 */
@Data
public class PerformanceManageScoreSecondVo {

    /**
     * 子级模板明细ID
     */
    @NotNull(message = "子级模板明细ID不能为空")
    private Long id;

    /**
     * 子级模板明细标题
     */
    @NotBlank(message = "子级模板明细ID不能为空")
    private String title;

    /**
     * 子级评分占比
     */
    @NotNull(message = "子级评分占比不能为空")
    private Integer scoreRate;

    /**
     * 子级满分
     */
    @NotNull(message = "子级满分不能为空")
    private BigDecimal fullScore;

    /**
     * 子级打分
     */
    @NotNull(message = "子级打分不能为空")
    private Integer mark;

    /**
     * 子级实际分值
     */
    @NotNull(message = "子级实际分值不能为空")
    private BigDecimal score;

    /**
     * 子级说明
     */
    private String info;

}
