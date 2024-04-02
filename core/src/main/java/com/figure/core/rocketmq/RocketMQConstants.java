package com.figure.core.rocketmq;

public class RocketMQConstants {

    public static final String ROCKETMQ_CONSUMER_GROUP = "frp_consumer";


    public static final String REAL_TIME_INDICATOR_GROUP="real_time_indicator_group";


    public static final String ROCKETMQ_ALL_TAGS = "*";

    public static final String FROM_WHERE = "CENTER";

    /**
     * 平台对前端的信号报警广播
     */
    public static final String SIGNAL_ALARM_UPDATE_P2S = "signal_alarm_update_p2s";

    /**
     * 信号报警状态更新，前端报警持续时间内每秒1次上报
     */
    public static final String SIGNAL_ALARM_UPDATE_S2P = "signal_alarm_update_s2p";

    /**
     * 前端向平台请求报警查询
     */
    public static final String SIGNAL_ALARM_CHECK_P2S = "signal_alarm_check_s2p";

    /**
     * 平台回复前端的报警查询
     */
    public static final String SIGNAL_ALARM_CHECK_S2P = "signal_alarm_check_s2p";


    /**
     * 前端向平台发送的倒换日志
     */
    public static final String SIGNAL_SWITCH_LOG_S2P = "signal_switch_log_s2p";


    /**
     * 平台向前端的录制任务下发
     */
    public static final String RECORD_TASK_SET_P2S = "record_task_set_p2s";

    /**
     * 前端向平台的返回录制任务下发结果
     */
    public static final String RECORD_TASK_SET_S2P = "record_task_set_s2p";

    /**
     * 前端向平台发送查询录制任务
     */
    public static final String RECORD_SERVICE_CHECK_S2P = "record_service_check_s2p";

    /**
     * 平台向前端发送录制任务列表
     */
    public static final String RECORD_SERVICE_CHECK_P2S = "record_service_check_p2s";


    /**
     * 前端向平台发送录制服务参数接收响应
     */
    public static final String RECORD_SERVICE_SET_S2P = "record_service_set_s2p";

    /**
     * 平台向前端发送录制服务参数
     */
    public static final String RECORD_SERVICE_SET_P2S = "record_service_set_p2s";

    /**
     * 平台向前端发送录制任务控制
     */
    public static final String RECORD_TASK_CONTROL_P2S = "record_task_control_p2s";

    /**
     * 前端向平台发送录制任务响应
     */
    public static final String RECORD_TASK_CONTROL_S2P = "record_task_control_s2p";

    /**
     * 平台向前端发送录制回看下载请求
     */
    public static final String RECORD_DATA_GET_P2S = "record_data_get_p2s";

    /**
     * 前端向平台发送录制回看下载响应
     */
    public static final String RECORD_DATA_GET_S2P = "record_data_get_s2p";

    /**
     * 前端向平台发送录制文件上报
     */
    public static final String RECORD_DATA_UPDATE_S2P = "record_data_update_s2p";

    /**
     * 前端向平台发送录制任务状态上报
     */
    public static final String RECORD_TASK_STATUS_S2P = "record_task_status_s2p";

    /**
     * 平台向前端发送转码规则
     */
    public static final String TRANSCODE_RULE_SET_P2S = "transcode_rule_set_p2s";

    /**
     * 前端向平台发送转码规则应答
     */
    public static final String TRANSCODE_RULE_SET_S2P = "transcode_rule_set_s2p";

    /**
     * 平台向前端发送转码任务查询应答
     */
    public static final String TRANSCODE_SERVICE_CHECK_P2S = "transcode_service_check_p2s";

    /**
     * 前端向平台发送转码任务查询
     */
    public static final String TRANSCODE_SERVICE_CHECK_S2P = "transcode_service_check_s2p";

    /**
     * 平台向前端发送控制转码任务信息
     */
    public static final String TRANSCODE_TASK_CONTROL_P2S = "transcode_task_control_p2s";

    /**
     * 前端向平台发送控制转码任务应答
     */
    public static final String TRANSCODE_TASK_CONTROL_S2P = "transcode_task_control_s2p";

    /**
     * 平台向前端发送转码任务信息
     */
    public static final String TRANSCODE_TASK_SET_P2S = "transcode_task_set_p2s";

    /**
     * 前端向平台发送转码任务信息应答
     */
    public static final String TRANSCODE_TASK_SET_S2P = "transcode_task_set_s2p";

    /**
     * 前端向平台发送转码任务状态
     */
    public static final String TRANSCODE_TASK_STATUS_S2P = "transcode_task_status_s2p";

    /**
     * 平台向前端发送服务远程控制
     */
    public static final String SERVICE_CONTROL_SET = "service_control_set_p2s";

    /**
     * 前端向平台发送实时频谱数据上报
     */
    public static final String SPECTRUM_ANALYSIS_DATA_S2P = "spectrum_analysis_data_s2p";

    /**
     * 前端向平台发送频谱异常报警上报
     */
    public static final String SPECTRUM_ANALYSIS_ALARM_S2P = "spectrum_analysis_alarm_s2p";

    /**
     * 平台向前端发送更新频谱采集参数
     */
    public static final String SPECTRUM_ANALYSIS_SET_P2S = "spectrum_analysis_set_p2s";

    /**
     * 平台向前端发送更新频谱参考线
     */
    public static final String SPECTRUM_REFERLINE_SET_P2S = "spectrum_referline_set_p2s";

    /**
     * 下发服务重启指令 至 网管数据上报服务
     */
    public static final String SERVICE_RESTART_SET_P2S = "service_restart_set";

    /**
     * 下发更新消息至 网管数据上报服务
     */
    public static final String SERVICE_PARAMETER_UPDATE_P2S = "service_parameter_update";

    public static final String All_TOPIC = SIGNAL_ALARM_UPDATE_S2P + "||" + RECORD_DATA_UPDATE_S2P;

    /**
     * 服务远程控制命令 1、服务重启
     */
    public static final Integer SERVICE_RESTART = 1;

    /**
     * 录制任务关理操作动作 1启动任务 对未启动任务有效
     */
    public static final Integer RECORD_CONTROL_START = 1;

    /**
     * 录制任务关理操作动作 2停止任务 对执行中任务有效
     */
    public static final Integer RECORD_CONTROL_STOP = 2;

    /**
     * 录制任务关理操作动作 3重启任务 对未启动任务有效，对执行中任务停止再启动
     */
    public static final Integer RECORD_CONTROL_RESTART = 3;

    /**
     * isAll, 0不是全部有效，1对全部任务有效
     */
    public static final Integer IS_ALL_NO = 0;

    /**
     * isAll, 0不是全部有效，1对全部任务有效
     */
    public static final Integer IS_ALL_YES = 1;

    /**
     * actionFlag 0正常执行，1执行故障
     */
    public static final Integer ACTION_FLAG_NORMAL = 0;

    /**
     * actionFlag 0正常执行，1执行故障
     */
    public static final Integer ACTION_FLAG_ERROR = 1;

    /**
     * 消息业务类型 1添加，2删除，3修改
     */
    public static final Integer BUSINESSCODE_ADD = 1;

    /**
     * 消息业务类型 1添加，2删除，3修改
     */
    public static final Integer BUSINESSCODE_DEL = 2;

    /**
     * 消息业务类型 1添加，2删除，3修改
     */
    public static final Integer BUSINESSCODE_EDIT = 3;

    /**
     * 任务消息业务类型 1.启动任务，2.自动停止，3.手动停止，4.重启任务
     */
    public static final Integer TASK_CODE_ACTION = 1;
    /**
     * 任务消息业务类型 1.启动任务，2.自动停止，3.手动停止，4.重启任务
     */
    public static final Integer TASK_CODE_AUTO_STOP = 2;
    /**
     * 任务消息业务类型 1.启动任务，2.自动停止，3.手动停止，4.重启任务
     */
    public static final Integer TASK_CODE_MANUAL_STOP = 3;
    /**
     * 任务消息业务类型 1.启动任务，2.自动停止，3.手动停止，4.重启任务
     */
    public static final Integer TASK_CODE_REBOOT = 4;

    /**
     * 消息请求类型 0请求 1应答
     */
    public static final Integer INSTRUCTERTYPE_REQUEST = 0;

    /**
     * 消息请求类型 0请求 1应答
     */
    public static final Integer INSTRUCTERTYPE_RESPONSE = 1;

    /**
     * 指标
     */
    public static final String REAL_TIME_INDICATOR = "device_status";

    /**
     * 指标 for 历史指标收录
     */
    public static final String REAL_TIME_INDICATOR_FOR_HISTORY = "device_status_for_history";

    /**
     * 指标 for 设备在线检查
     */
    public static final String REAL_TIME_INDICATOR_FOR_ONLINE_CHECK = "device_status_for_online_check";

    /**
     * 指标 for 设备状态变更
     */
    public static final String REAL_TIME_INDICATOR_FOR_STATE_CHANGE = "device_status_for_state_change";


    /**
     * 指标设值响应
     */
    public static final String INDEX_SET_RESPONSE = "index_set_response";


    /**
     * 指标设值响应组
     */
    public static final String INDEX_SET_RESPONSE_GROUP = "index_set_response_group";
    public static final String DEVICE_ALARM = "device_alarm" ;
    public static final String DEVICE_ALARM_GROUP = "device_alarm_group" ;

    /**
     * 心跳响应
     */
    public static final String COLLECT_SERVER_HEARTBEAT = "collect_server_heartbeat" ;

    /**
     * 心跳
     */
    public static final String HEARTBEAT = "heartbeat" ;


    /**
     * 播出系统监测业务设置更新
     */

    public static final String SYSTEM_MONITOR_PARAMETER_SET = "system_monitor_parameter_set" ;

    /**
     * 采集服务监测设备状态类型的指标值发生变更
     */
    public static final String DEVICE_STATE_INDICATOR_CHANGE_P2S = "device_state_indicator_change_p2s";

    /**
     * 前端向平台发送的服务状态信息
     */
    public static final String SERVICE_STATUS = "service_status";
}
