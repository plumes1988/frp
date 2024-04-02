package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * <p>
 * 报警对象与报警门限规则关联
 * </p>
 *
 * @author feather
 * @date 2021-12-13 16:56:37
 */
@Data
@ApiModel(value = "SignalAlarmthresholdStreamRelList", description = "报警对象与报警门限规则关联list")
public class SignalAlarmthresholdStreamRelList {

    @ApiModelProperty("报警对象与报警门限规则关联list")
    private List<SignalAlarmthresholdStreamRel> signalAlarmthresholdStreamRelJson;
}