package com.figure.core.service.alarm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.alarm.AlarmKey;
import com.figure.core.model.alarm.AlarmMessageInfo;
import com.figure.core.rocketmq.struct.consumer.SignalAlarmCheckS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.SignalAlarmUpdateS2PConsumer;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 信号报警记录 IService
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
public interface IAlarmMessageInfoService extends IService<AlarmMessageInfo> {

    void processAlarmUpdate(SignalAlarmUpdateS2PConsumer consumer);

    Map<AlarmKey, AlarmMessageInfo> getBeginAlarmMap();

    void setBeginAlarmMap(Map<AlarmKey, AlarmMessageInfo> alarmMessageInfoMap);

    void addBeginAlarmByAlarmKey(AlarmKey alarmKey, AlarmMessageInfo alarmMessageInfo);

    AlarmMessageInfo removeAlarmByAlarmKey(AlarmKey alarmKey);

    void sendAlarmUpdate(AlarmMessageInfo alarmMessageInfo);

    void sendAlarmListUpdate(List<AlarmMessageInfo> alarmMessageInfoList, String serviceCode);

    void processAlarmCheck(SignalAlarmCheckS2PConsumer consumer);

    void initCache();
}