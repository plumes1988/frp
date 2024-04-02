package com.figure.op.performance.domain.vo.manage;

import com.figure.op.performance.domain.bo.PerformanceManageScoreFirstBo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
* 绩效评分管理 得分情况回显
 * @author 39827
 */
@Data
public class PerformanceManageScoreRespVO {

    /**
     * 机构id
     */
    private Integer agencyId;

    /**
     * 机构名称
     */
    private String agencyName;

    /**
     * 绩效评价ID
     */
    private Long manageId;

    /**
     * 评价总分
     */
    private BigDecimal totalScore;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 绩效明细打分列表
     */
    List<PerformanceManageScoreFirstVo> scoreList;


}
