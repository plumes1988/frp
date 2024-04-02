package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 排班-值班任务关联表
 * @author fsn
 */
@Data
@TableName("duty_schedule_info_duty")
public class DutyScheduleInfoDuty {

    /**
     * ID
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 排班管理ID
     */
    @TableField("scheduleId")
    private Integer scheduleId;

    /**
     * 值班任务ID
     */
    @TableField("dutyId")
    private Integer dutyId;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;


}
