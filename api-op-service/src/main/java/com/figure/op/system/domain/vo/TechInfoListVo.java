package com.figure.op.system.domain.vo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 *
 * @author fsn
 */
@Data
public class TechInfoListVo extends BaseEntity {

    private Integer techInfoId;

    private String techName;

    private String techType;

    private String techDes;

    private Integer isDelete;

}
