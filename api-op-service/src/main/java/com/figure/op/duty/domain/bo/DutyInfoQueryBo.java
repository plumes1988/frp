package com.figure.op.duty.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 品牌查询对象
 * @author fsn
 */
@Data
public class DutyInfoQueryBo {

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
