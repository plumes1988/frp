package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class SignalSwitchLogS2PConsumer extends MessageBase {

    /**
     * 发送指令服务终端编号
     */
    private String serviceCode;

    /**
     * 倒换设备编号
     */
    private String deviceCode;

    /**
     * 倒换类型 1自动倒换 2手动倒换
     */
    private Integer isAuto;

    /**
     * 操作动作 1应急 2回切 3强制
     */
    private Integer actionMode;

    /**
     * 倒换信息
     */
    private String actionInfo;

    /**
     * 报警开始时间
     */
    private String actionTime;

    /**
     * 倒换失败原因
     */
    private String alarmContent;

    /**
     * 手动倒换操作用户Id
     */
    private Integer userId;
}
