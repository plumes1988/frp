package com.figure.core.redis;

public class RedisConstants {

    /**
     * 采集站list
     */
    public static final String FRONT_STATION_INFO_LIST = "frontStationInfoList";

    /**
     * 采集点list
     */
    public static final String FRONT_LOGIC_POSITION_LIST = "frontLogicPositionList";

    /**
     * 频道list
     */
    public static final String SIGNAL_CHANNEL_INFO_LIST = "signalChannelInfoList";

    /**
     * 频率list
     */
    public static final String SIGNAL_FREQUENCY_INFO_LIST = "signalFrequencyInfoList";

    /**
     * 播出系统list
     */
    public static final String SIGNAL_LOGIC_CHANNEL_LIST = "signalLogicChannelList";

    /**
     * 根据positionId获取position的map
     */
    public static final String POSITION_BY_POSITIONID_MAP = "positionByPositionIdMap";


    /**
     * 根据frontId获取frontStation的map
     */
    public static final String FRONTSTATION_BY_FRONTID_MAP = "frontStationByFrontIdMap";

    /**
     * 根据channelCode获取logic的map
     */
    public static final String LOGIC_BY_CHANNELCODE_MAP = "logicByChannelCodeMap";

    /**
     * 根据channelId获取channel的map
     */
    public static final String CHANNEL_BY_CHANNELID_MAP = "channelByChannelIdMap";

    /**
     * 根据frequencyId获取frequency的map
     */
    public static final String FREQUENCY_BY_FREQUENCYID_MAP = "frequencyByFrequencyIdMap";
    /**
     * 报警类型list
     */
    public static final String LOGIC_THRESHOLD_INFO_LIST = "logicThresholdInfoList";

    /**
     * 根据报警类型number获取报警类型实例
     */
    public static final String LOGIC_THRESHOLD_BY_NUMBER_MAP = "logicThresholdByNumberMap";

    /**
     * 系统参数表中报警参数
     */
    public static final String SYS_PARA = "sysPara";

    /**
     * 系统参数表中报警参数名称
     */
    public static final String ALARM_PARAM = "alarmParam";

    /**
     * 系统参数表中录制参数名称
     */
    public static final String RECORD_PARAM = "recordParam";

    /**
     * 当前发生的报警map
     */
    public static final String CURR_ALARM_MAP = "currAlarmMap";


    /**
     * 当前发生的频谱报警map
     */
    public static final String CURR_SPECTRUM_ALARM_MAP = "currSpectrumAlarmMap";

    /**
     * 当前发生的服务报警map
     */
    public static final String SERVICE_STATUS_ALARM_MAP = "serviceStatusAlarmMap";
    /**
     * 当前发生的报警分布式锁
     */
    public static final String CURR_ALARM_MAP_LOCK = "currAlarmMapLock";

    /**
     * 当前发生的报警事件map
     */
    public static final String CURR_EVENT_MAP = "currEventMap";

    /**
     * 当前发生的频谱报警分布式锁
     */
    public static final String CURR_SPECTRUM_ALARM_MAP_LOCK = "currSpectrumAlarmMapLock";

    /**
     * 当前发生的服务报警分布式锁
     */
    public static final String SERVICE_STATUS_ALARM_LOCK = "serviceStatusAlarmLock";

    /**
     * 当前发生的服务报警分布式锁
     */
    public static final String SERVICE_STATUS_ALARM_LAST_UPDATE_TIME = "serviceStatusAlarmLastUpdateTime";

    /**
     * 接收的频谱数据
     */
    public static final String CURR_SPECTRUM_DATA_MAP = "currSpectrumDataMap";

    /**
     * 频谱仪
     */
    public static final String CURR_SPECTRUM_DEVICE_MAP = "currSpectrumDeviceMap";


    /**
     * 通知规则
     */
    public static String NOTICE_STRUCT = "noticeStruct";


    /**
     * 设备指标收录模式
     */
    public static String DEVICE_HISTORY_INDICATOR_RECORD_MODE = "deviceHistoryIndicatorRecordMode";

    /**
     * 设备指标变更门限
     */
    public static String DEVICE_HISTORY_INDICATOR_CHANGE_THRESHOLD = "deviceHistoryIndicatorChangeThreshold";

    /**
     * 设备Id—设备编号-map
     */
    public static String DEVICEID_DEVICECODE_MAP = "deviceId_deviceCode_map";

    /**
     * 设备编号-设备Id-map
     */
    public static String DEVICECODE_DEVICEID_MAP = "deviceCode_deviceId_map";

    /**
     *  为报警回看历史数据专门保存历史数据
     */
    public static String QUEUE_NAME_SAVE_INDICATOR_HISTORY_FOR_ALARM = "queue_name_save_indicator_history_for_alarm";

    /**
     * 设备指标编号-数据类型-map
     */
    public static String INDICATORCODE_DATATYPE_MAP = "indicatorCode_dataType_map";


    /**
     * 设备指标编号-前端Id-map
     */
    public static String DEVICECODE_MONITORSTATIONID_MAP = "deviceCode_monitorStationId_map";


    /**
     * 设备指标编号-离线判定门限-map
     */
    public static String DEVICECODE_OFFLINEDECISIONTHRESHOLD_MAP = "devicecode_offlinedecisionthreshold_map";

    /**
     * 设备指标编号-设备信息-map
     */
    public static String DEVICECODE_DEVICEINFO_MAP = "devicecode_deviceinfo_map";

    /**
     * 监测设备
     */
    public static String MONITOR_DEVICES = "monitor_devices";



}

