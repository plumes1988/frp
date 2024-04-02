package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("repair_event_execute")
public class RepairEventExecute extends BaseEntity {

    @TableId(value = "executeId")
    private Integer executeId;

    @TableField("repairEventId")
    private String repairEventId;

    @TableField("executeStatus")
    private String executeStatus;

    @TableField("workerId")
    private Integer workerId;

    @TableField("executeDes")
    private String executeDes;

    @TableField("repairDes")
    private String repairDes;

    @TableField("repairRestore")
    private String repairRestore;

    @TableField("tryResult")
    private String tryResult;

    @TableField("repairImgs")
    private String repairImgs;

}
