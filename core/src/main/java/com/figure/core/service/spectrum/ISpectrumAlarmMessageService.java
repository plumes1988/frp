package com.figure.core.service.spectrum;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.spectrum.SpectrumAnalysisAlarmKey;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.rocketmq.struct.consumer.SpectrumAnalysisAlarmS2PConsumer;

import java.util.Map;

/**
 * <p>
 * 频谱报警记录 IService
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
public interface ISpectrumAlarmMessageService extends IService<SpectrumAlarmMessage> {

    void acceptAlarm(SpectrumAnalysisAlarmS2PConsumer consumer);

    Map<SpectrumAnalysisAlarmKey, SpectrumAlarmMessage> getBeginAlarmMap();

    void setBeginAlarmMap(Map<SpectrumAnalysisAlarmKey, SpectrumAlarmMessage> alarmMessageInfoMap);

    void addBeginAlarmByAlarmKey(SpectrumAnalysisAlarmKey spectrumAnalysisAlarmKey, SpectrumAlarmMessage alarmMessageInfo);

    SpectrumAlarmMessage removeAlarmByAlarmKey(SpectrumAnalysisAlarmKey spectrumAnalysisAlarmKey);

    void saveTraceData(SpectrumAlarmMessage spectrumAlarmMessage);
}