package com.figure.op.duty.domain.vo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class RepairEventExecuteListVo extends BaseEntity {

    private Integer executeId;

    private String repairEventId;

    private String executeStatus;

    private Integer workerId;

    private String executeDes;

    private String repairDes;

    private String repairRestore;

    private String tryResult;

    private String repairImgs;

}
