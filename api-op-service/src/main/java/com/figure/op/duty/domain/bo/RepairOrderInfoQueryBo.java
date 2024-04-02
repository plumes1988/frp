package com.figure.op.duty.domain.bo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class RepairOrderInfoQueryBo extends BaseEntity {

    private Integer repairOrderId;

    private Integer stationId;

    private String station;

    private Integer deviceId;

    private String device;

    private String fault;

    private String imgs;

    private Date submitTime;

    private Integer submitterId;

    private String submitter;

    private String submitStatus;
    // 退回理由
    private String submitReason;

    private Date reviewTime;

    private Integer reviewerId;

    private String reviewer;

    private String reviewStatus;

    private String reviewReason;

    private Date actTime;

    private Integer actorId;

    private String actor;

    private String actStatus;

    private String repairDes;

    private String repairImgs;

    // 维修工单 0  审核工单 1  执行工单 2
    @NotNull
    private String pageType;

    // 提交 审核 执行开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    // 提交 审核 执行结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
