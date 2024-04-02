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
 * 报警对象与报警门限规则关联
 * </p>
 *
 * @author feather
 * @date 2021-12-13 16:56:37
 */
@Data
@Accessors(chain = true)
@TableName("signal_alarmthreshold_stream_rel")
@EqualsAndHashCode(callSuper = false)
public class SignalAlarmthresholdStreamRel extends BaseModel {

    /**
     * 1码流 2逻辑频道 3频道
     */
    private Integer objectType;
    /**
     * 码流ID，逻辑频道ID，频道ID
     */
    @TableId(type = IdType.INPUT)
    private String objectId;
    /**
     * 报警门限规则ID
     */
    private Integer ruleId;
    /**
     * 规则应用优先级
     */
    private Integer priority;
    /**
     * 启用状态 0不启用 1启用
     */
    private Integer isEnable;
    /**
     * 状态：0:未删除、1:删除
     */
    private Integer isDelete;

}