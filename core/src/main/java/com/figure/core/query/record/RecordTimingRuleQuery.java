package com.figure.core.query.record;

import com.figure.core.model.record.RecordTimingRule;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RecordTimingRuleQuery extends AbstractQuery<RecordTimingRule> {

    @Eq
    @ApiModelProperty("规则ID")
    private Integer ruleId;

    @Eq
    @ApiModelProperty("任务ID")
    private Integer recordTaskId;

    @Eq
    @ApiModelProperty("排列顺序")
    private Integer serno;

    @Eq
    @ApiModelProperty("是否全天 0:是 1：否")
    private Integer isFullDay;

    @Eq
    @ApiModelProperty("开始时间")
    private Date startTime;

    @Eq
    @ApiModelProperty("结束时间")
    private Date endTime;

    @Eq
    @ApiModelProperty("规则类型：1每天、2每周、3单次")
    private Integer regularMode;

    @Eq
    @ApiModelProperty("星期几（按周时可以多选一周中的星期） 格式采用java日期对应编码星期天为1 顺沿星期六为7  如周一 周三  则数据为 2:4")
    private String dayOfWeek;

    @Eq
    @ApiModelProperty("工作日是否循环：0单次执行、1循环执行")
    private Integer workRepeatMode;

    @Eq
    @ApiModelProperty("开始日期")
    private Date startDate;

    @Eq
    @ApiModelProperty("结束日期")
    private Date endDate;

}