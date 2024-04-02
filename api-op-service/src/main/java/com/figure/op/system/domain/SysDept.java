package com.figure.op.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_department_info")
public class SysDept extends BaseEntity {

    @TableId(value = "departmentId")
    private Integer deptId;

    @TableField("departmentName")
    private String deptName;

    @TableField("departmentType")
    private String departmentType;

    @TableLogic
    private Integer isDelete;

}
