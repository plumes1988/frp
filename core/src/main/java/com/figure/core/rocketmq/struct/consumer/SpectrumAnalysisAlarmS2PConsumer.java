package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

import java.util.List;

@Data
public class SpectrumAnalysisAlarmS2PConsumer extends MessageBase {

    /**
     * 采集服务编号
     */
    private String serviceCode;

    /**
     * 频谱仪设备编号
     */
    private String spectrumCode;

    /**
     * 报警ID
     */
    private Long alarmID;

    /**
     * 中心频率
     */
    private Integer centerFrequency;

    /**
     * 带宽
     */
    private Integer frequencySpan;

    /**
     * 开始频率
     */
    private Integer startFrequency;

    /**
     * 结束频率
     */
    private Integer endFrequency;

    /**
     * 实时频谱
     */
    private List<Integer> traceData;

    /**
     * 平均频谱
     */
    private List<Integer> traceDataAverage;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 持续时间
     */
    private Integer alarmDuration;

    /**
     * 电平
     */
    private Integer level;

    /**
     * 载噪比单位dB
     */
    private String CNR;

    /**
     * 报警状态 0恢复 1持续
     */
    private Integer alarmFlag;
}
