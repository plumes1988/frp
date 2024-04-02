package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.figure.core.base.BaseModel;


/**
 * <p>
 * 信号报警门限规则
 * </p>
 *
 * @author feather
 * @date 2021-12-13 16:56:37
 */
@Data
@Accessors(chain = true)
@TableName("signal_alarmthreshold_rule")
@EqualsAndHashCode(callSuper = false)
public class SignalAlarmthresholdRule extends BaseModel {

    /**
     * 规则ID
     */
    @TableId(type = IdType.AUTO)
    private Integer ruleId;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 规则描述
     */
    private String ruleDescription;
    /**
     * 下发状态 1已下发 0未下发
     */
    private Integer synStatus;
    /**
     * 启用状态 0不启用 1启用
     */
    private Integer isEnable;
    /**
     * 状态：0:未删除、1:删除
     */
    private Integer isDelete;

}