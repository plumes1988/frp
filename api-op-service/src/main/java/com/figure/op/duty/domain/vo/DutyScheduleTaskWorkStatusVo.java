package com.figure.op.duty.domain.vo;

import lombok.Data;

/**
 * 排班任务值班记录对象
 * @author fsn
 */
@Data
public class DutyScheduleTaskWorkStatusVo {

    /**
     * 值班记录ID
     */
    private Integer workRecordId;

    /**
     * 值班记录-值班任务名称
     */
    private String dutyName;

    /**
     * 值班记录标题
     */
    private String workTitle;

    /**
     * 执行结果（0未完成 1已完成）
     */
    private String actResult;


}
