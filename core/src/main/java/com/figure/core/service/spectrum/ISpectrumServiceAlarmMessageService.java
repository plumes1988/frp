package com.figure.core.service.spectrum;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumAnalysisAlarmKey;
import com.figure.core.model.spectrum.SpectrumDeviceAlarmKey;
import com.figure.core.model.spectrum.SpectrumServiceAlarmMessage;
import com.figure.core.query.spectrum.SpectrumServiceAlarmMessageQuery;
import com.figure.core.rocketmq.struct.consumer.ServiceStatusS2PConsumer;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频谱服务报警记录 IService
 * </p>
 * 
 * @author feather
 * @date 2024-03-06 13:22:17
 */
public interface ISpectrumServiceAlarmMessageService extends IService<SpectrumServiceAlarmMessage> {


    void processSpectrumServiceAlarmMessage(ServiceStatusS2PConsumer consumer);

    Map<SpectrumDeviceAlarmKey, SpectrumServiceAlarmMessage> getBeginAlarmMap();

    SpectrumServiceAlarmMessage removeAlarmByAlarmKey(SpectrumDeviceAlarmKey SpectrumDeviceAlarmKey);
}