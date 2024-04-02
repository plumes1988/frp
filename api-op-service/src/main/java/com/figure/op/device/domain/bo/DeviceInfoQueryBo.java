package com.figure.op.device.domain.bo;

import lombok.Data;

/**
 * 设备查询对象
 * @author fsn
 */
@Data
public class DeviceInfoQueryBo {

    /**
     * 所属监测站ID（采集站）
     */
    private Integer frontId;

    /**
     * 关联采集点（采集点）
     */
    private Integer positionId;

    /**
     * 设备归类（设备类型）
     */
    private String deviceType;

    /**
     * 设备子类
     */
    private String deviceSubType;

    /**
     * 设备型号
     */
    private Integer modelId;

    /**
     * 设备名称
     */
    private String deviceName;



}
