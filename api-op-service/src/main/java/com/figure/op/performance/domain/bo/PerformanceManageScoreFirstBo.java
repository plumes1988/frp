package com.figure.op.performance.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;

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
public class PerformanceManageScoreFirstBo {

    /**
     * 父级模板明细ID
     */
    @NotNull(message = "父级模板明细ID不能为空")
    private Long id;

    /**
     * 父级模板明细标题
     */
    @NotBlank(message = "父级模板明细标题不能为空")
    private String title;

    /**
     * 父级评分占比
     */
    @NotNull(message = "父级评分占比不能为空")
    private Integer scoreRate;

    /**
     * 父级满分
     */
    @NotNull(message = "父级满分不能为空")
    private BigDecimal fullScore;

    /**
     * 父级实际分值
     */
    @NotNull(message = "父级实际分值不能为空")
    private BigDecimal score;

    /**
     * 子级打分列表
     */
    @NotNull(message = "子级打分列表不能为空")
    @Valid
    private List<PerformanceManageScoreSecondBo> secondList;

}
