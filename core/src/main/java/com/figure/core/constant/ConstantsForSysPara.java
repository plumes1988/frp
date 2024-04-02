package com.figure.core.constant;

import java.util.HashMap;
import java.util.Map;

public class ConstantsForSysPara {

    /**
     * 设备网管相关
     */
        /**
         * 在线状态指标编号
         */
        public static String ONLINE_STATUS_INDICATOR_CODE =  "onlineStatusIndicatorCode";

        /**
         * 报警状态指标编号
         */
        public static String  ALARM_STATUS_INDICATOR_CODE =  "alarmStatusIndicatorCode";

        /**
         * 离线报警类型ID
         */
        public static String  OFFLINE_ALARM_TYPE_ID =  "offlineAlarmTypeId";

        /**
         * 被监测设备离线判断间隔时长（秒）
         */
        public static String  OFFLINE_JUDGMENT_INTERVAL_OF_MONITORED_DEVICE =  "offlineJudgmentIntervalOfMonitoredDevice";

        /**
         * 采集服务离线判断间隔时长
         */
        public static String  OFFLINE_DETECTION_INTERVAL_DURATION =  "offlineDetectionIntervalDuration";


        /**
         * mq新消息判定延迟门限（秒）
         */
        public static String  NEW_MSG_FROM_MQ_DELAY_THRESHOLD =  "newMsgFromMqDelayThreshold";


        /**
         * 初始加载最近报警的条数
         */
        public static String  NUMBER_OF_RECENT_ALARMS =  "numberOfRecentAlarms";

        /**
         * 未变更指标历史收录间隔 (秒)
         */
        public static String  UNCHANGED_INDICATOR_HISTORICAL_COLLECTION_INTERVAL =  "unchangedIndicatorHistoricalCollectionInterval";


        /**
         * 实时指标web推送间隔门限 (秒)'
         */
        public static String  LIVE_INDICATOR_SEND_TO_WEB_GAP_THRESHOLD =  "liveIndicatorSendToWebGapThreshold";



    /**
     * 设备网管相关
     */


    /**
     * 初始密码(密码重置会将密码重置为初始密码)
     */
    public static String INITIAL_PASSWORD =  "initialPassword";


    public static Map<String,String>  DEFAULT_PARAM_VALUES  = new HashMap<String,String>(){
        {
           put(NEW_MSG_FROM_MQ_DELAY_THRESHOLD,"5");
           put(INITIAL_PASSWORD,"111111");
        }
    };


}
