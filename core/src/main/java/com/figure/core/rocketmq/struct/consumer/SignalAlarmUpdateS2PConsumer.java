package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.helper.DateHelper;
import com.figure.core.model.alarm.AlarmMessageInfo;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class SignalAlarmUpdateS2PConsumer extends MessageBase {

    /**
     * 播出系统(逻辑频道)编号
     */
    private String systemCode;

    /**
     * 报警来源 1音频报警，2视频报警，3传输异常，4音频比对异常，5视频比对异常
     */
    private Integer mediaType;

    /**
     * 信号Id
     */
    private String signalID;

    /**
     * 参考信号Id
     */
    private String signalID_Ref;

    /**
     * 报警类型
     */
    private Integer alarmType;

    /**
     * 报警开始时间
     */
    private String startTime;

    /**
     * 报警持续时间 单位ms
     */
    private Integer duration;

    /**
     * 报警状态 0恢复 1报警
     */
    private Integer alarmFlag;

    public SignalAlarmUpdateS2PConsumer(AlarmMessageInfo alarmMessageInfo) {
        super(RocketMQConstants.SIGNAL_ALARM_UPDATE_S2P, "*");
        this.systemCode = alarmMessageInfo.getSystemCode();
        this.mediaType = alarmMessageInfo.getMediaType();
        this.signalID = alarmMessageInfo.getSignalId();
        this.signalID_Ref = alarmMessageInfo.getRefSignalId();
        this.alarmType = alarmMessageInfo.getAlarmTypeId();
        this.startTime = DateHelper.format(alarmMessageInfo.getStartTime());
        this.duration = alarmMessageInfo.getDuration();
        this.alarmFlag = alarmMessageInfo.getAlarmFlag();
    }
}
