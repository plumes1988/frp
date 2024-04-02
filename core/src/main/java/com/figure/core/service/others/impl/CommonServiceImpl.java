package com.figure.core.service.others.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.model.device.*;
import com.figure.core.model.front.FrontStationInfo;
import com.figure.core.model.sys.SysDataDictionary;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.repository.others.CommonMapper;
import com.figure.core.service.device.*;
import com.figure.core.service.front.IFrontStationInfoService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.service.sys.ISysDataDictionaryService;
import com.figure.core.service.sys.ISysUserInfoService;
import com.figure.core.util.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.figure.core.constant.Constants.OFFLINE;
import static com.figure.core.model.device.DeviceIndicatorParam.UNIT_TAG;
import static com.figure.core.model.device.DeviceServiceAlarmMessage.*;
import static com.figure.core.model.device.DeviceServiceAlarmMessage.CONFIRMED;

@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    CommonMapper commonMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Autowired
    IFrontStationInfoService frontStationInfoService;

    @Autowired
    IDeviceLocateService deviceLocateService;

    @Resource
    private IDeviceTypeService deviceTypeService;

    @Autowired
    ISysDataDictionaryService sysDataDictionaryService;

    @Autowired
    IDeviceIndicatorParamService deviceIndicatorParamService;

    @Resource
    private ISysUserInfoService sysUserInfoService;

    @Resource
    private IDeviceAlarmtypeInfoService deviceAlarmtypeInfoService;

    @Resource
    private IDeviceServiceAlarmMessageService deviceServiceAlarmMessageService;

    @Override
    public List<Map<String, Object>> queryTable(String columns,String tableName, String conditions) {
        try {
            columns = java.net.URLDecoder.decode(columns, StandardCharsets.UTF_8.name());
            tableName = java.net.URLDecoder.decode(tableName, StandardCharsets.UTF_8.name());
            conditions = java.net.URLDecoder.decode(conditions, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return commonMapper.queryTable(columns,tableName,conditions);
    }

    @Override
    public List<Map<String, Object>> querySql(String sql) {
        return commonMapper.querySql(sql);
    }

    @Override
    public void createDeviceHistoryIndicatorTable(String deviceCode) {
        String tableName = "device_history_indicator_"+deviceCode;
        if(!isTableExist(tableName)){
            try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
                String createTableSQL ="CREATE TABLE `"+tableName+"` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                        "  `deviceId` int(11) DEFAULT NULL COMMENT '设备ID',\n" +
                        "  `deviceCode` varchar(255) DEFAULT NULL COMMENT '设备编号',\n" +
                        "  `indicatorCode` varchar(255) DEFAULT NULL COMMENT '指标编号',\n" +
                        "  `indicatorValue` varchar(255) DEFAULT NULL COMMENT '实时数据',\n" +
                        "  `updateTime` datetime DEFAULT NULL COMMENT '指标接收、更新时间',\n" +
                        "  `collectTime` varchar(255) DEFAULT NULL COMMENT '指标采集服务采集时间',\n" +
                        "  `createTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                        "  `receiveTime` datetime DEFAULT NULL COMMENT '获取实时指标时间',\n" +
                        "                        PRIMARY KEY (`id`) USING BTREE,\n" +
                        "                KEY `indicatorCode查询用` (`indicatorCode`) USING BTREE,\n" +
                        "                KEY `collectTime查询用` (`collectTime`) USING BTREE\n" +
                        ")";
                sqlSession.getConnection().createStatement().execute(createTableSQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isTableExist(String tableName) {
        try  (SqlSession sqlSession = sqlSessionFactory.openSession(true))  {
            DatabaseMetaData metaData = sqlSession.getConnection().getMetaData();
            try (ResultSet resultSet = metaData.getTables(null, null, tableName, null)) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Map<String, String> get_deviceCode_deviceName_map() {
        Map<String, String>  result = new HashMap<>();
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("deviceCode", "deviceName"); // 指定要查询的列
        List<DeviceInfo> deviceInfos = deviceInfoService.list(queryWrapper);
        for(DeviceInfo deviceInfo:deviceInfos){
            result.put(deviceInfo.getDeviceCode(),deviceInfo.getDeviceName());
        }
        return result;
    }

    @Override
    public Map<Integer, String> get_frontId_frontName_map() {
        Map<Integer, String>  result = new HashMap<>();
        QueryWrapper<FrontStationInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("frontId", "frontName"); // 指定要查询的列
        List<FrontStationInfo> frontStationInfos = frontStationInfoService.list(queryWrapper);
        for(FrontStationInfo frontStationInfo:frontStationInfos){
            result.put(frontStationInfo.getFrontId(),frontStationInfo.getFrontName());
        }
        return result;
    }

    @Override
    public Map<Integer, String> get_localId_localName_map() {
        Map<Integer, String>  result = new HashMap<>();
        QueryWrapper<DeviceLocate> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("locateId", "locateName"); // 指定要查询的列
        List<DeviceLocate> deviceLocates = deviceLocateService.list(queryWrapper);
        for(DeviceLocate deviceLocate:deviceLocates){
            result.put(deviceLocate.getLocateId(),deviceLocate.getLocateName());
        }
        return result;
    }

    @Override
    public Map<String, String> get_deviceTypeCode_deviceTypeName_map() {
        Map<String, String>  result = new HashMap<>();
        QueryWrapper<DeviceType> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("typeCode", "typeName"); // 指定要查询的列
        List<DeviceType> deviceTypes = deviceTypeService.list(queryWrapper);
        for(DeviceType deviceType:deviceTypes){
            result.put(deviceType.getTypeCode(),deviceType.getTypeName());
        }
        return result;
    }

    @Override
    public Map<String, Map<String, String>> get_indicatorCode_value_label_map() {
        Map<String, Map<String, String>> result = new HashMap<>();
        QueryWrapper<DeviceIndicatorParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("indicatorCode", "dataType","statusParaType");
        List<DeviceIndicatorParam> deviceIndicatorParams = deviceIndicatorParamService.list(queryWrapper);

        Map<String, String> unit_tag_map = new HashMap<>();

        for(DeviceIndicatorParam deviceIndicatorParam:deviceIndicatorParams){
            Map<String, String> map = new HashMap<>();
            Integer DataType = deviceIndicatorParam.getDataType();
            if(DataType==DeviceIndicatorParam.STATUS_VARIABLE){
                String statusParaType = deviceIndicatorParam.getStatusParaType();
                List<SysDataDictionary> sysDataDictionaries =  sysDataDictionaryService.list(new QueryWrapper<SysDataDictionary>()
                        .eq("paraType","indicator_param_status")
                        .eq("parentParaValue",statusParaType));
                for (SysDataDictionary sysDataDictionary:sysDataDictionaries){
                    map.put(sysDataDictionary.getParaValue(),sysDataDictionary.getParaName());
                }
            }
            if(DataType==DeviceIndicatorParam.SWITCH_VARIABLE){
                map.put("0","关");
                map.put("1","开");
            }
            result.put(deviceIndicatorParam.getIndicatorCode(),map);

            String unit = deviceIndicatorParam.getUnit();

            if(DataType==DeviceIndicatorParam.NUMBER_VARIABLE && !StringUtils.isEmpty(unit)){
                unit_tag_map.put(deviceIndicatorParam.getIndicatorCode(),unit);
            }
        }
        result.put(UNIT_TAG,unit_tag_map);
        return result;
    }

    @Override
    public Map<Long, String> get_userId_userName_map() {
        Map<Long, String>  result = new HashMap<>();
        List<SysUserInfo>  list = sysUserInfoService.list();
        for(SysUserInfo sui:list){
            result.put(sui.getUserId(),sui.getUserName());
        }
        return result;
    }

    @Override
    public Map<String, String> get_indicatorCode_indicatorName_map() {
        Map<String, String>  result = new HashMap<>();
        QueryWrapper<DeviceIndicatorParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("indicatorCode", "indicatorName"); // 指定要查询的列
        List<DeviceIndicatorParam> deviceIndicatorParams = deviceIndicatorParamService.list(queryWrapper);
        for(DeviceIndicatorParam deviceIndicatorParam:deviceIndicatorParams){
            result.put(deviceIndicatorParam.getIndicatorCode(),deviceIndicatorParam.getIndicatorName());
        }
        return result;
    }

    @Override
    public Map<Integer, String> get_alarmTypeId_alarmTypeName_map() {
        Map<Integer, String>  result = new HashMap<>();
        QueryWrapper<DeviceAlarmtypeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("alarmTypeId", "alarmTypeName"); // 指定要查询的列
        List<DeviceAlarmtypeInfo> deviceAlarmtypeInfos = deviceAlarmtypeInfoService.list(queryWrapper);
        for(DeviceAlarmtypeInfo deviceAlarmtypeInfo:deviceAlarmtypeInfos){
            result.put(deviceAlarmtypeInfo.getAlarmTypeId(),deviceAlarmtypeInfo.getAlarmTypeName());
        }
        return result;
    }

    @Override
    public Map<Long, SysUserInfo> get_userId_sysUserInfo_map() {
        Map<Long, SysUserInfo>  result = new HashMap<>();
        List<SysUserInfo> list = sysUserInfoService.list();
        for(SysUserInfo sui:list){
            result.put(sui.getUserId(),sui);
        }
        return result;
    }

    @Override
    public Map<String, Integer> get_collectService_online_status_map() {

        Map<String, Integer> result = new HashMap<>();

        QueryWrapper queryWrapper  = new QueryWrapper();
        queryWrapper.eq("serviceType",SERVICE_TYPE_COLLECT);
        queryWrapper.eq("alarmType",ALARMTYPE_OFFLINE);
        queryWrapper.eq("status",NOT_END);
        queryWrapper.in("confirm", Arrays.asList(new String[]{UN_CONFIRMED,CONFIRMED}));

        List<DeviceServiceAlarmMessage> deviceServiceAlarmMessages =  deviceServiceAlarmMessageService.list(queryWrapper);

        for(DeviceServiceAlarmMessage deviceServiceAlarmMessage:deviceServiceAlarmMessages){
            result.put(deviceServiceAlarmMessage.getServiceCode(),OFFLINE);
        }

        return result;
    }

}
