package com.figure.core.model.spectrum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.rocketmq.struct.consumer.SpectrumAnalysisAlarmS2PConsumer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("spectrum_alarm_message")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlarmKey", description = "信号报警记录key")
public class SpectrumAnalysisAlarmKey {

    private String serviceCode;

    private String spectrumCode;

    private Long alarmID;

    public SpectrumAnalysisAlarmKey() {

    }

    public SpectrumAnalysisAlarmKey(SpectrumAnalysisAlarmS2PConsumer consumer) {
        this.serviceCode = consumer.getServiceCode();
        this.spectrumCode = consumer.getSpectrumCode();
        this.alarmID = consumer.getAlarmID();
    }


    public SpectrumAnalysisAlarmKey(SpectrumAlarmMessage spectrumAlarmMessage) {
        this.serviceCode = spectrumAlarmMessage.getServiceCode();
        this.spectrumCode = spectrumAlarmMessage.getSpectrumCode();
        this.alarmID = Long.parseLong(spectrumAlarmMessage.getAlarmType().toString());
    }
}
