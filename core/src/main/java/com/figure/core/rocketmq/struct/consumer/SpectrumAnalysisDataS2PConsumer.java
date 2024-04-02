package com.figure.core.rocketmq.struct.consumer;

import com.alibaba.fastjson.annotation.JSONField;
import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@Data
@JsonIgnoreProperties({"messageHead", "rocketmqTopic", "rocketmqTag"})
public class SpectrumAnalysisDataS2PConsumer extends MessageBase {

    /**
     * 采集服务编号
     */
    private String serviceCode;

    /**
     * 频谱仪设备编号
     */
    private String spectrumCode;

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
     * 功率dBμV
     */
    private Integer level;

    /**
     * 载噪比dB
     */
    @JSONField(name = "CNR")
    private String CNR;

    @Override
    public String toString() {
        return "SpectrumAnalysisDataS2PConsumer{" +
                "serviceCode='" + serviceCode + '\'' +
                ", spectrumCode='" + spectrumCode + '\'' +
                ", startFrequency=" + startFrequency +
                ", endFrequency=" + endFrequency +
                ", traceData=" + traceData +
                ", traceDataAverage=" + traceDataAverage +
                '}';
    }
}
