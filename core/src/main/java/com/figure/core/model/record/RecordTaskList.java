package com.figure.core.model.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "RecordTaskList", description = "完整录制任务结构：任务本身和任务时间规则")
public class RecordTaskList {

    @ApiModelProperty("录制任务List")
    private List<RecordTaskInfo> recordTaskInfoJson;

    @ApiModelProperty("录制任务时间规则List")
    private List<RecordTimingRule> recordTimingRuleJson;
}
