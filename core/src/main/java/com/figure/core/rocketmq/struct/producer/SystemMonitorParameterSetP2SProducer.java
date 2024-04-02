package com.figure.core.rocketmq.struct.producer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.record.RecordFile;
import com.figure.core.model.record.RecordPlayback;
import com.figure.core.model.signal.SignalSourceMonitor;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.rocketmq.struct.info.RecordFileInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SystemMonitorParameterSetP2SProducer extends MessageBase {

    private String systemCode;
    private String progarmListChannelCode;
    private Integer collectProgramListEnable;
    private Integer monitorEnable;
    private Integer alarmRuleID;
    private Integer preMonitorEnable;

    private Integer preTime1st;

    private Integer preAlamRuleID1st;

    private Integer preTime2nd;

    private Integer preAlarmRuleID2nd;

    private Integer testProgramMonitorEnable;

    private String testProgramCode;

    private Integer noProgramListAudioFormat;

    public SystemMonitorParameterSetP2SProducer(SignalSourceMonitor signalSourceMonitor){
        super(RocketMQConstants.SYSTEM_MONITOR_PARAMETER_SET, RocketMQConstants.ROCKETMQ_ALL_TAGS);
        this.systemCode = signalSourceMonitor.getLogicChannelCode();
        this.progarmListChannelCode = signalSourceMonitor.getProgramListChannelCode();
        this.collectProgramListEnable = signalSourceMonitor.getCollectProgramListEnable();
        this.monitorEnable = signalSourceMonitor.getMonitorEnable();
        this.alarmRuleID = signalSourceMonitor.getAlarmThresholdModel();
        this.preMonitorEnable = signalSourceMonitor.getPreMonitorEnable();
        this.preTime1st = signalSourceMonitor.getPre1stTime();
        this.preTime2nd = signalSourceMonitor.getPre2stTime();
        this.preAlamRuleID1st = signalSourceMonitor.getPre1stAlarmThresholdModel();
        this.preAlarmRuleID2nd = signalSourceMonitor.getPre2stAlarmThresholdModel();
        this.testProgramMonitorEnable  = signalSourceMonitor.getTestProgramMonitorEnable();
        this.testProgramCode = signalSourceMonitor.getTestProgramCode();
        this.noProgramListAudioFormat = signalSourceMonitor.getNoProgramListAudioFormat();
    }
}
