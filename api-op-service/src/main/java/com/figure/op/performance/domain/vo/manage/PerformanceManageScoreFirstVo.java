package com.figure.op.performance.domain.vo.manage;

import com.figure.op.performance.domain.bo.PerformanceManageScoreSecondBo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 绩效评分-打分对象
 * @author fsn
 */
@Data
public class PerformanceManageScoreFirstVo {

    /**
     * 父级模板明细ID
     */
    private Long id;

    /**
     * 父级模板明细标题
     */
    private String title;

    /**
     * 父级评分占比
     */
    private Integer scoreRate;

    /**
     * 父级满分
     */
    private BigDecimal fullScore;

    /**
     * 父级实际分值
     */
    private BigDecimal score;

    /**
     * 子级打分列表
     */
    private List<PerformanceManageScoreSecondVo> secondList;

}
