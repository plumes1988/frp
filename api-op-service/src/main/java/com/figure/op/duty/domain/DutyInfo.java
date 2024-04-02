package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("duty_info")
public class DutyInfo extends BaseEntity {

    /**
     * 值班任务ID
     */
    @TableId(value = "dutyId")
    private Integer dutyId;

    /**
     * 任务名称
     */
    @TableField("dutyName")
    private String dutyName;

    /**
     * 任务类型
     */
    @TableField("dutyType")
    private String dutyType;

    /**
     * 任务描述
     */
    @TableField("dutyDesc")
    private String dutyDesc;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;


}
