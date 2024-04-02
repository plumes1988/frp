package com.figure.core.service.device;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceHistoryIndicator;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.rocketmq.message.DeviceItemInfo;
import com.figure.core.rocketmq.message.DeviceStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-06-30
 */
public interface IDeviceHistoryIndicatorService extends IService<DeviceHistoryIndicator> {
    void saveHistoryIndicator(DeviceStatus deviceStatus, DeviceItemInfo indexInfo, String messageTime, Date updateTime,Date receiveTime);

    int saveHistoryIndicator(String deviceCode,DeviceHistoryIndicator entity);

    void deleteByCondition(String deviceCode,Date createTime,String indicatorCode);

    IPage<DeviceHistoryIndicator> selectPage(Page<DeviceHistoryIndicator> page, Map<String,String> conditions);

    void saveHistoryIndicatorForAlarm(String deviceCode,String indicatorCode, Date start,Date end);

    void fillEntityProps(List<DeviceHistoryIndicator> records);
}
