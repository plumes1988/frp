package com.figure.op.duty.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class RepairOrderInfoListVo extends BaseEntity {

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

    private String repairStationName;
    
}
