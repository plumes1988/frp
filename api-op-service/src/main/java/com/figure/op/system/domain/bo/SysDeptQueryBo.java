package com.figure.op.system.domain.bo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 *
 * @author fsn
 */
@Data
public class SysDeptQueryBo extends BaseEntity {

    private Integer deptId;

    private String deptName;

    private String departmentType;

}
