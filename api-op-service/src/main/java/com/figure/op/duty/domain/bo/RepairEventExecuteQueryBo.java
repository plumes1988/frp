package com.figure.op.duty.domain.bo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class RepairEventExecuteQueryBo extends BaseEntity {

    private Integer executeId;

    private String repairEventId;

    private String executeStatus;

    private Integer workerId;

    private String executeDes;

    private String repairDes;

    private String repairRestore;

    private String tryResult;

    private String repairImgs;

    /**
     * 检修事件执行日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date eventExecuteDate;
}
