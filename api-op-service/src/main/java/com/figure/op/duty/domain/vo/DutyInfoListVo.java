package com.figure.op.duty.domain.vo;

import lombok.Data;

/**
 * 列表视图对象
 * @author fsn
 */
@Data
public class DutyInfoListVo {

    /**
     * 值班任务ID
     */
    private Integer dutyId;

    /**
     * 任务名称
     */
    private String dutyName;

    /**
     * 任务类型
     */
    private String dutyType;

    /**
     * 任务描述
     */
    private String dutyDesc;
}
