package com.figure.core.constant;

/**
 * 通用常量信息
 *
 * @author feather
 */
public class Constants {

    /** 生产环境标识 */
    public static final String PRODUCTION = "pro";
    /** 开发环境标识 */
    public static final String DEVELOPMENT = "dev";

    /** UTF-8 字符集 */
    public static final String UTF8 = "UTF-8";

    /** 1024 */
    public static final Integer KB = 1024;

    /** 是 */
    public static final String YES = "Y";
    /** 否 */
    public static final String NO = "N";

    /** 默认用户 */
    public static final String DEFAULT_USER = "admin";
    /** 演示标识符 */
    public static final Long DEMO_TAG = 0L;

    /** 通用成功标识 */
    public static final String SUCCESS = "0";
    /** 通用失败标识 */
    public static final String FAIL = "1";

    /** 登录成功 */
    public static final String LOGIN_SUCCESS = "Success";
    /** 注销 */
    public static final String LOGOUT = "Logout";
    /** 登录失败 */
    public static final String LOGIN_FAIL = "Error";

    /** 字典类型是否唯一的返回结果码 */
    public static final String DICT_TYPE_UNIQUE = "0";
    public static final String DICT_TYPE_NOT_UNIQUE = "1";

    /** 参数键名是否唯一的返回结果码 */
    public static final String CONFIG_KEY_UNIQUE = "0";
    public static final String CONFIG_KEY_NOT_UNIQUE = "1";

    /** 字典正常状态 */
    public static final String DICT_NORMAL = "0";

    /** 数据库类型-MySQL */
    public static final String DATABASE_TYPE_MYSQL = "mysql";
    /** 数据库类型-Oracle */
    public static final String DATABASE_TYPE_ORACLE = "oracle";
    /** 数据库类型-PostgreSQL */
    public static final String DATABASE_TYPE_POSTGRESQL = "pgsql";
    /** 数据库类型-DB2 */
    public static final String DATABASE_TYPE_DB2 = "db2";
    /** 数据库类型-SQL Server */
    public static final String DATABASE_TYPE_SQLSERVER = "sqlserver";

    /** Oracle数据库连接类型-SID */
    public static final String ORACLE_CONN_TYPE_SID = "sid";
    /** Oracle数据库连接类型-服务名 */
    public static final String ORACLE_CONN_TYPE_SERVICE_NAME = "service_name";

    /**
     * isEnable字段状态 0 停用
     */
    public static final Integer DATATABLE_ISENABLE_DISABLED = 0;
    /**
     * isEnable字段状态 1 启用
     */
    public static final Integer DATATABLE_ISENABLE_ENABLED = 1;

    /**
     * isDelete字段状态 0 未删除
     */
    public static final Integer DATATABLE_ISDELETE_NOTDELETED = 0;
    /**
     * isDelete字段状态 1 已删除
     */
    public static final Integer DATATABLE_ISDELETE_DELETED = 1;

    /**
     * synStatus字段状态 0 未同步
     */
    public static final Integer DATATABLE_SYNSTATUS_NOTSYNCED = 0;
    /**
     * synStatus字段状态 1 已同步
     */
    public static final Integer DATATABLE_SYNSTATUS_SYNCED = 1;

    /**
     * 报警处理状态 0未确认
     */
    public static final Integer COMMITFLAG_UNCONFIRM = 0;

    /**
     * 报警处理状态 1事故
     */
    public static final Integer COMMITFLAG_EVENT = 1;

    /**
     * 报警处理状态 2误报
     */
    public static final Integer COMMITFLAG_FALSEALARM = 2;

    /**
     * 报警进行中
     */
    public static final Integer ALARM_ONGOING = 1;

    /**
     * 报警结束
     */
    public static final Integer ALARM_STOP = 0;

    /**
     * 报警事件进行中
     */
    public static final Integer EVENT_ONGOING = 1;

    /**
     * 报警事件结束
     */
    public static final Integer EVENT_STOP = 0;

    /**
     * 报警恢复阈值
     */
    public static final String ALARM_TIMEOUT = "alarmTimeout";


    /**
     * 报警恢复阈值
     */
    public static final String EVENT_TIME = "eventTime";

    /**
     * HLSfilePath
     */
    public static final String HLS_FILE_PATH = "HLSfilePath";

    /**
     * localIP
     */
    public static final String LOCAL_IP = "localIP";

    /**
     * fileTime
     */
    public static final String FILE_TIME = "fileTime";

    /**
     * HLSfile
     */
    public static final String HLS_FILE_TIME = "HLSfile";

    /**
     * 任务状态 0 待执行
     */
    public static final Integer TASK_WAIT = 0;

    /**
     * 任务状态 1 正常执行
     */
    public static final Integer TASK_NORMAL = 1;

    /**
     * 任务状态 2 自动中断
     */
    public static final Integer TASK_AUTO_INTERRUPTION = 2;

    /**
     * 任务状态 3 手动中断
     */
    public static final Integer TASK_MANUAL_INTERRUPTION = 3;

    /**
     * 任务状态 4 故障中断
     */
    public static final Integer TASK_ERROR_INTERRUPTION = 4;

    /**
     * 任务状态 5 信源中断
     */
    public static final Integer TASK_SIGNAL_INTERRUPTION = 5;

    /**
     * 转码媒体类型1UDP、2HLS、3RTMP、4HTTP_FLV
     */
    public static final Integer TRANSCODE_UDP = 1;

    /**
     * 转码媒体类型1UDP、2HLS、3RTMP、4HTTP_FLV
     */
    public static final Integer TRANSCODE_HLS = 2;

    /**
     * 转码媒体类型1UDP、2HLS、3RTMP、4HTTP_FLV
     */
    public static final Integer TRANSCODE_RTMP = 4;

    /**
     * 转码媒体类型1UDP、2HLS、3RTMP、4HTTP_FLV
     */
    public static final Integer TRANSCODE_HTTP_FLV = 8;

    /**
     * MQ发送消息失败
     */
    public static final String ROCKETMQ_SEND_ERROR = "MQ发送消息失败";

    /**
     * 未找到相关设备
     */
    public static final String DEVICE_NOT_FOUND = "未找到相关设备";

    /**
     * MQ发送消息失败
     */
    public static final String DEVICE_ENABLESETTING_NO = "设备改指标项目未开启设置";

    /**
     * 指标设置时，提示与当前值相同，无需设置
     */
    public static final String SAME_VALUE_NOT_NEED_TO_SET = "指标设置时，提示与当前值相同，无需设置!";

    /**
     * 指标设置时，提示与当前值相同，无需设置
     */
    public static final String DATA_OUT_OF_BOUNDS = "所设置值已越界!";

    /**
     * 指标设置时，提示设备未设置该指标
     */
    public static final String HAS_NO_DEVICE_INDICATOR_REL = "设备未设置该指标!";

    /**
     * 设置指标超过5秒为响应，判定为超时
     */
    public static final String SET_INDICATOR_TIMEOUT = "设置指标超过5秒未响应，判定为超时!";

    /**
     * 服务状态 0正常
     */
    public static Integer SERVICE_STATUS_NORMAL = 0;

    /**
     * 服务状态 1报警
     */
    public static Integer SERVICE_STATUS_ALARM = 1;

    /**
     * 系统参数名
     */
    public static Integer DEFAULT_COLLECT_SERVICE_CHECK_PORT = 8084;


    /**
     * 离线
     */
    public static Integer OFFLINE = 0;


    /**
     * 在线
     */
    public static Integer ONLINE = 1;


    /**
     *查询数据，这些属性采用完全匹配
     */
    public static String EXACT_MATCH_FIELDS = "exactMatchFields";
}
