package com.figure.op.system.domain.vo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 *
 * @author fsn
 */
@Data
public class SysFrontStationListVo extends BaseEntity {

    private Integer frontId;

    private String frontName;

    private Integer supFrontId;

    private String frontType;

}
