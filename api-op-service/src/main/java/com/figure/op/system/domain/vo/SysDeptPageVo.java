package com.figure.op.system.domain.vo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 *
 * @author fsn
 */
@Data
public class SysDeptPageVo extends BaseEntity {

    private Integer deptId;

    private String deptName;

    private String departmentType;

}
