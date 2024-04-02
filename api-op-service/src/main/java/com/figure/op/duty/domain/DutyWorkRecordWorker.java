package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 值班记录-值班人员关联表
 * @author fsn
 */
@Data
@TableName("duty_work_record_worker")
public class DutyWorkRecordWorker {

    /**
     * ID
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 值班记录ID
     */
    @TableField(value = "workRecordId")
    private Integer workRecordId;

    /**
     * 值班人员ID
     */
    @TableField("workerId")
    private Integer workerId;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;


}
