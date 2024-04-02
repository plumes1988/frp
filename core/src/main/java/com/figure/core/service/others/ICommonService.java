package com.figure.core.service.others;

import com.figure.core.model.sys.SysUserInfo;

import java.util.List;
import java.util.Map;

public interface ICommonService {

    List<Map<String, Object>> queryTable(String columns,String tableName, String conditions);

    List<Map<String, Object>> querySql(String sql);

    void createDeviceHistoryIndicatorTable(String deviceCode);

    Map<String,String> get_deviceCode_deviceName_map();

    Map<Integer, String> get_frontId_frontName_map();

    Map<Integer, String> get_localId_localName_map();

    Map<String, String> get_deviceTypeCode_deviceTypeName_map();

    Map<String, Map<String, String>> get_indicatorCode_value_label_map();

    Map<Long, String> get_userId_userName_map();

    Map<String, String> get_indicatorCode_indicatorName_map();

    Map<Integer, String> get_alarmTypeId_alarmTypeName_map();

    Map<Long, SysUserInfo> get_userId_sysUserInfo_map();

    Map<String, Integer> get_collectService_online_status_map();
}
