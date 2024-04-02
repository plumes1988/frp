package com.figure.core.service.device;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.device.DeviceInfoList;
import com.figure.core.model.device.ServiceControlSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-15
 */
public interface IDeviceInfoService extends IService<DeviceInfo> {

    /**
     * @Description: 根据前端ID获取设备列表
     * @Param: [frontId]
     * @return: java.util.List<com.figure.core.model.device.DeviceInfo>
     * @Author: feather
     * @Date: 2021/10/15
     */
    List<DeviceInfo> queryAvailableDeviceInfoByFrontId(Integer frontId);

    /**
     * @Description: 根据前端ID设置设备的更新状态
     * @Param: [frontId]
     * @return: boolean
     * @Author: feather
     * @Date: 2021/10/15
     */
    boolean updateBatchSynStatusByFrontId(Integer frontId);

    List<DeviceInfoList> treelist(QueryWrapper<DeviceInfo> autoPageWrapper);

    List<DeviceInfoList> interbydevice(QueryWrapper<DeviceInfo> autoPageWrapper);

    Integer getDeviceIdByDeviceCode(String  deviceCode);

    String getDeviceCodeByDeviceId(Integer deviceId);

    DeviceInfo getDeviceByDeviceCode(String  deviceCode);

    Map<String, Object> restartServiceByCode(ServiceControlSet serviceControlSet);

    Map<Integer,String> getDeviceId_deviceCodeMap();

    void setDeviceId_deviceCodeEachMapCache();

    Map<String, Integer> getDeviceCode_deviceIdMap();

    Map<String, Integer> getDeviceCodeFrontIdMapFromCache();

    void setDeviceCodeFrontIdMapCache();

    Map<String, Integer> getDeviceCodeOfflineDecisionThresholdMapFromCache();

    void setDeviceCodeOfflineDecisionThresholdMapCache();

    Map<String, DeviceInfo> getDeviceCodeDeviceInfoMapFromCache();

    void setDeviceCodeDeviceInfoMapCache();

    void updateCache();

    List<HashMap<String, Object>> detailList(QueryWrapper<DeviceInfo> deviceInfoQueryWrapper);

    List<DeviceInfo> getMonitorDevicesFromCache();

    void setMonitorDevicesFromCache();
}
