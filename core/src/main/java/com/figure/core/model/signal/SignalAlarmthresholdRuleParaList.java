package com.figure.core.model.signal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "SignalAlarmthresholdRuleParaList", description = "多个规则和报警类型关联List")
public class SignalAlarmthresholdRuleParaList {

    @ApiModelProperty("规则和报警类型关联List")
    private List<SignalAlarmthresholdRulePara> signalAlarmthresholdRuleParaJson;
}
