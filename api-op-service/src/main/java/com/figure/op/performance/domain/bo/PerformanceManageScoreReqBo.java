package com.figure.op.performance.domain.bo;

import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 绩效评分-打分对象
 * @author fsn
 */
@Data
public class PerformanceManageScoreReqBo {

    /**
     * 绩效评价ID
     */
    @NotNull(message = "绩效评价ID不能为空")
    private Long manageId;

    /**
     * 评价总分
     */
    @NotNull(message = "评价总分不能为空")
    private BigDecimal totalScore;

    /**
     * 绩效明细打分列表
     */
    @NotNull(message = "子项打分列表不能为空")
    @Valid
    List<PerformanceManageScoreFirstBo> scoreList;

}
