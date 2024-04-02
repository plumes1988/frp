package com.figure.core.rocketmq.struct.info;

import com.figure.core.helper.DateHelper;
import com.figure.core.model.alarm.AlarmMessageInfo;
import lombok.Data;

@Data
public class AlarmInfo {

    public AlarmInfo(AlarmMessageInfo alarmMessageInfo) {
        this.mediaType = alarmMessageInfo.getMediaType();
        this.systemCode = alarmMessageInfo.getSystemCode();
        this.systemName = alarmMessageInfo.getSystemName();
        this.frontID = alarmMessageInfo.getFrontId();
        this.frontName = alarmMessageInfo.getFrontName();
        this.logicPositionID = alarmMessageInfo.getLogicPositionId();
        this.logicPositionName = alarmMessageInfo.getLogicPositionName();
        this.signalID = alarmMessageInfo.getSignalId();
        this.signalName = alarmMessageInfo.getSignalName();
        this.systemCodeRef = alarmMessageInfo.getRefSystemCode();
        this.systemNameRef = alarmMessageInfo.getRefSystemName();
        this.frontIDRef = alarmMessageInfo.getRefFrontId();
        this.frontNameRef = alarmMessageInfo.getRefFrontName();
        this.logicPositionIDRef = alarmMessageInfo.getRefLogicPositionId();
        this.logicPositionNameRef = alarmMessageInfo.getRefLogicPositionName();
        this.signalIDRef = alarmMessageInfo.getRefSignalId();
        this.signalNameRef = alarmMessageInfo.getRefSignalName();
        this.alarmType = alarmMessageInfo.getAlarmTypeId();
        this.startTime = DateHelper.defaultFormat(alarmMessageInfo.getStartTime());
        this.duration = alarmMessageInfo.getDuration();
        this.alarmFlag = alarmMessageInfo.getAlarmFlag();
    }

    /**
     * 报警来源 1音频报警 2视频报警，3传输异常，4音频比对异常，5视频比对异常
     */
    private Integer mediaType;

    /**
     * 播出系统（逻辑频道）编号
     */
    private String systemCode;

    /**
     * 播出系统（逻辑频道）编号
     */
    private String systemName;

    /**
     * 采集点Id
     */
    private Integer frontID;

    /**
     * 采集站名称
     */
    private String frontName;

    /**
     * 采集点id
     */
    private Integer logicPositionID;


    /**
     * 采集点名称
     */
    private String logicPositionName;

    /**
     * 信号Id
     */
    private String signalID;

    /**
     * 信号名称
     */
    private String signalName;

    /**
     * 参考播出系统（逻辑频道）编号
     */
    private String systemCodeRef;

    /**
     * 参考播出系统（逻辑频道）名称
     */
    private String systemNameRef;

    /**
     * 参考采集站Id
     */
    private Integer frontIDRef;

    /**
     * 参考采集站名称
     */
    private String frontNameRef;

    /**
     * 参考采集点Id
     */
    private Integer logicPositionIDRef;

    /**
     * 参考采集点名称
     */
    private String logicPositionNameRef;

    /**
     * 参考信号Id
     */
    private String signalIDRef;

    /**
     * 参考信号名称
     */
    private String signalNameRef;

    /**
     * 报警类型
     */
    private Integer alarmType;

    /**
     * 报警开始时间
     */
    private String startTime;

    /**
     * 报警持续时间（ms）
     */
    private Integer duration;

    /**
     * 报警状态 0恢复 1报警中
     */
    private Integer alarmFlag;
}
