package com.figure.op.duty.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class RepairEventInfoListVo extends BaseEntity {

    private Integer repairEventId;

    private Integer repairTaskId;

    private Integer workerId;

    private String worker;

    private Date startTime;

    private Date endTime;

    private String deviceId;

    private Integer stationId;

    private String device;

    private String station;

    private String repairAct;

    private String confirmStatus;

    private String confirmer;

    private Integer confirmerId;

    private String actStatus;

    private String isDelete;

    private String taskName;

    private String deviceName;

    private String frontName;

    private List<RepairEventExecuteListVo> executeListVos;
}
