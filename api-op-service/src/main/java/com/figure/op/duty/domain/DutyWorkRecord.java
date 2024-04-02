package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 值班记录
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("duty_work_record")
public class DutyWorkRecord extends BaseEntity {

    /**
     * 值班记录ID
     */
    @TableId(value = "workRecordId")
    private Integer workRecordId;

    /**
     * 排班计划ID
     */
    @TableField("scheduleId")
    private Integer scheduleId;

    /**
     * 排班任务ID
     */
    @TableField("scheduleTaskId")
    private Integer scheduleTaskId;

    /**
     * 排班日期
     */
    @TableField("scheduleDate")
    private Date scheduleDate;

    /**
     * 值班任务ID
     */
    @TableField("dutyId")
    private Integer dutyId;

    /**
     * 记录人员ID集合
     */
    @TableField("workerIds")
    private String workerIds;

    /**
     * 记录时间
     */
    @TableField("recordTime")
    private Date recordTime;

    /**
     * 记录信息
     */
    @TableField("recordDesc")
    private String recordDesc;

    /**
     * 资料上传路径
     */
    @TableField("attachPath")
    private String attachPath;

    /**
     * 执行人员ID
     */
    @TableField("actWorkerId")
    private Integer actWorkerId;

    /**
     * 执行结果（0未完成 1已完成）
     */
    @TableField("actResult")
    private String actResult;

    /**
     * 执行时间
     */
    @TableField("actTime")
    private Date actTime;

    /**
     * 详细描述
     */
    @TableField("detailDesc")
    private String detailDesc;

    /**
     * 值班记录标题
     */
    @TableField("workTitle")
    private String workTitle;

    /**
     * 记录类型 1排班计划自动生成 2手动添加
     */
    @TableField("cate")
    private String cate;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;
}
