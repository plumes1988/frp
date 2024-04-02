/**
 * 
 */
package com.figure.core.constant;


/**
 * Company : Figure Information Technology Co. Ltd.
 * 说明：
 * 共通常量key值
 * @author jijw
 * @create 2011-2-11 下午03:23:25
 */
public class CommonConstant {
	
	/**
	 * 频点类型
	 */
	public final static String frequencyType ="frequencyType";
	
	/**
	 * 频率状态
	 */
	public final static String frequencyState ="frequencyState";
	
	/**
	 * 异常状态
	 */
	public final static String abnormalState ="abnormalState";
	
	/**
	 * 信号频点单位
	 */
	public final static String signalUnit ="signalUnit";
	
	/**
	 * 信号类别 0 :未选定信号类型 
	 */
	public final static String signalSort_nothing = "0";
	
	/**
	 * 信号类别 1:模拟信号
	 */
	public final static String signalSort_analog = "1";
	
	/**
	 * 信号类别 2:数字信号
	 */
	public final static String signalSort_digital = "2";
	
	/**
	 * 监测站初始化状态 0:完成初始化
	 */
	public final static String monitor_initStatus_Ok = "0";
	
	/**
	 * 监测站初始化状态 1：未完成初始化
	 */
	public final static String monitor_initStatus_No = "1";
	
	/**
	 * 显示状态  0：已同步
	 */
	public final static String syncStatus_0 = "0";
	
	/**
	 * 显示状态  1：编辑
	 */
	public final static String syncStatus_1 = "1";
	
	/**
	 * 显示状态  2：扫频新增
	 */
	public final static String syncStatus_2 = "4";
	
	/**
	 * 显示状态  3：报警新增
	 */
	public final static String syncStatus_3 = "5";
	
	/**
	 * 显示状态  4：手动新增
	 */
	public final static String syncStatus_4 = "6";
	
	/**
	 * 显示状态  5：新频
	 */
	public final static String syncStatus_5 = "2";
	/**
	 * 显示状态 6：待删除
	 */
	public final static String syncStatus_6 = "3";
	/**
	 * 显示状态7：未同步
	 */
	public final static String syncStatus_7 = "1";
	
	/**
	 * 新频状态0：已有频率(频率)0 x:2
	 */
	public final static String newFrequencyFlagf_0 = "1";
	
	/**
	 * 新频状态1：新频率(频率)1 x:0
	 */
	public final static String newFrequencyFlagf_1 = "0";
	
	/**
	 * 新频状态2：丢失频率(频率)2 x:1
	 */
	public final static String newFrequencyFlagf_2 = "2";
	
	/**
	 * 新频状态0：已有频率(频道)0 x:2
	 */
	public final static String newFrequencyFlagc_0 = "1";
	
	/**
	 * 新频状态1：新频率(频道)1 x:0
	 */
	public final static String newFrequencyFlagc_1 = "0";
	
	/**
	 * 新频状态2：丢失频率(频道)2 x:1
	 */
	public final static String newFrequencyFlagc_2 = "2";
	
	/**
	 * 新频状态3：忽略(频道)
	 */
	public final static String newFrequencyFlagc_3 = "3";
	
	/**
	 * 同步标识  0：合法
	 */
	public final static String syncFlag_0 = "0";
	
	/**
	 * 同步标识  1：非法
	 */
	public final static String syncFlag_1 = "1";
	
	/**
	 * 录制对象 1：频道
	 */
	public final static String recObjType_1 = "1";
	
	/**
	 * 录制对象 2：频率
	 */
	public final static String recObjType_2 = "2";
	
	/**
	 * 任务时间设置-是否全天  0：是
	 */
	public final static String taskTime_isFullDay_0 = "0";
	
	/**
	 * 任务时间设置-是否全天  1：否
	 */
	public final static String taskTime_isFullDay_1 = "1";
	
	/**
	 * 任务时间设置-定期模式  1：天
	 */
	public final static String taskTime_regularMode_1 = "1";
	
	/**
	 * 任务时间设置-定期模式  2：周
	 */
	public final static String taskTime_regularMode_2 = "2";
	
	/**
	 * 任务设置-指令  0：设置
	 */
	public final static Integer task_Instruct_0 = 0;
	
	/**
	 * 任务设置-指令  1：删除
	 */
	public final static Integer task_Instruct_1 = 1;
	
	/**
	 * 任务设置-执行结果  0：成功
	 */
	public final static Integer task_Result_0 = 0;
	
	/**
	 * 任务设置-执行结果  1：失败
	 */
	public final static Integer task_Result_1 = 1;
	
	/**
	 * 频道播放  1：开始
	 */
	public final static String playChannel_start = "1";
	
	/**
	 * 频道播放 0：停止
	 */
	public final static String playChannel_stop = "0";
	
	/**
	 * 频道手动录制  1：开始
	 */
	public final static String hmRecordChannel_start = "1";
	
	/**
	 * 频道手动录制 0：停止
	 */
	public final static String hmRecordChannel_stop = "0";
	
	/**
	 * 任务时间设置-is结束时间 0：无结束时间
	 */
	public final static Integer task_IsEndTime_0 = 0;
	
	/**
	 * 任务时间设置-is结束时间  1：有结束时间
	 */
	public final static Integer task_IsEndTime_1 = 1;
	
	/**
	 * 任务类型 1：频道任务
	 */
	public final static String taskType_1 = "1";
	
	/**
	 * 任务类型 2：指标任务
	 */
	public final static String taskType_2 = "2";
	
	/**
	 * 任务类型 3：扫频任务
	 */
	public final static String taskType_3 = "3";
	/**
	 * 手动录制类型 1：手动录制频道
	 */
	public final static String hmTaskType_1 = "1";
	
	/**
	 * 手动录制类型2：手动录制指标
	 */
	public final static String hmTaskType_2 = "2";
	
	/**
	 * 手动录制类型 3：手动录制扫频
	 */
	public final static String hmTaskType_3 = "3";
	
	/**
	 * 轮询任务1：是
	 */
	public final static String pollTask_0 = "0";
	
	/**
	 * 轮询任务2：否
	 */
	public final static String pollTask_1 = "1";
	
	/**
	 * 时间格式 ： 年月日时分秒
	 * yyyy-MM-dd HH:mm:ss
	 */
	public final static String date_DatetimeStr = "yyyy-MM-dd HH:mm:ss"; 
	
	/**
	 * 时间格式 ： 年月日 
	 * yyyy-MM-dd
	 */
	public final static String date_DateStr = "yyyy-MM-dd"; 
	
	/**
	 * 时间格式 ： 时分秒
	 * HH:mm:ss
	 */
	public final static String date_TimeStr = "HH:mm:ss"; 
	
	/**
	 * 每页显示条数
	 */
	public final static Integer pageSize  = 10;
	
	/**
	 * 任务设置 是否回传 0：是
	 */
	public final static Integer task_isBack_0 = 0;
	
	/**
	 * 任务设置 是否回传 0：否
	 */
	public final static Integer task_isBack_1 = 1;
	
	/**
	 * 任务设置 显示方式 1：日(显示1天)
	 */
	public final static String task_DateSize_1 = "1";
	
	/**
	 * 任务设置 显示方式 1：周（显示7天）
	 */
	public final static String task_DateSize_7 = "7";
	
	/**
	 * 信号类型单位： KHZ
	 */
	public final static String signalUtil_KHz = "1";
	
	/**
	 * 信号类型单位： MHZ
	 */
	public final static String signalUtil_MHz = "2";
	
	/**
	 * 信号类型单位：GHZ
	 */
	public final static String signalUtil_GHz = "3";
	
	/**
	 * 信号来源1： 手动添加
	 */
	public final static String signalTypeSource_1 = "1";
	
	/**
	 * 信号来源2： 扫频添加
	 */
	public final static String signalTypeSource_2 = "2";
	
	/**
	 * 扫描结果0：成功
	 */
	public final static Integer scan_Result_0 = 0;
	
	/**
	 * 扫描结果1：失败
	 */
	public final static Integer scan_Result_1 = 1;
	
	/**
	 * 日志等级3：ERROR
	 */
	public final static Integer logLevel_3 = 3;
	
	/**
	 * 日志等级3：ERROR
	 */
	public final static String  logLevel_3_error = "3";
	
	/**
	 * 日志等级2：INFO
	 */
	public final static Integer logLevel_2 = 2;
	
	/**
	 * 日志等级2：INFO
	 */
	public final static String  logLevel_2_info = "2";
	
	/**
	 * 日志等级1：WARN
	 */
	public final static Integer logLevel_1 = 1;
	
	/**
	 * 日志等级1：WARN
	 */
	public final static String logLevel_1_warn = "1";
	
	/**
	 * 日志来源1：前端
	 */
	public final static Integer logOrigin_1 = 1;
	
	/**
	 * 日志来源1：前端
	 */
	public final static String logOrigin_1_front = "1";
	
	/**
	 * 日志来源2：中心
	 */
	public final static Integer logOrigin_2 = 2;
	
	/**
	 * 日志来源2：中心
	 */
	public final static String logOrigin_2_center = "2";
	
	/**
	 * 日志来源3：SMG
	 */
	public final static String logOrigin_3_center = "2";
	
	
	/**
	 * 设备操作1: 重启
	 */
	public final static Integer deviceOpt_1 = 1;
	
	/**
	 * 设备操作2: 开关机
	 */
	public final static Integer deviceOpt_2 = 2;
	
	/**
	 * 设备操作3: 天馈倒换
	 */
	public final static Integer deviceOpt_3 = 3;
	
	/**
	 * 设备操作4: 主备切换
	 */
	public final static Integer deviceOpt_4 = 4;
	
	/**'
	 * 设备重启结束0:成功
	 */
	public final static Integer deviceOptResult_0 = 0;
	
	/**
	 * 删除标识0：已删除
	 */
	public final static String deleteFlog_0 = "0";
	
	/**'
	 * 删除标识1：正常
	 */
	public final static String deleteFlog_1 = "1";
	
	/**
	 * 日志类型5:服务端
	 */
	public final static Integer logType_5 = 5;
	
	/**
	 * 日志类型5:服务端
	 */
	public final static String logType_5_server = "5";
	
	/**
	 * 日志类型4:系统
	 */
	public final static String logType_4_system = "4";
	
	/**
	 * 温度单位：℃
	 */
	public final static String temperature_C = "℃";
	
	/**
	 * 湿度单位：%
	 */
	public final static String humidity_B = "%";
	
	/**
	 * 电压单位：V
	 */
	public final static String power_V = "V";
	
	/**
	 * 调制度单位：%
	 */
	public final static String amModulationInput_B = "%";
	
	/**
	 * 信号类型查询字符串长度3：转换大小写
	 */
	public final static Integer queryLength_3 = 3;
	
	/**
	 * 信号类型所属1：广播
	 */
	public final static int signalPertain_1 = 1;
	
	/**
	 * 信号类型所属2：电视
	 */
	public final static int signalPertain_2 = 2;
	
	/**
	 * 行政区域级别1：省
	 */
	public final static Integer regionLevel_one = 1;
	
	/**
	 * 行政区域级别1：市
	 */
	public final static Integer regionLevel_two = 2;
	
	/**
	 * 行政区域级别1：县
	 */
	public final static Integer regionLevel_three = 3;
	
	/**
	 * 预留宽带
	 */
	public final static Integer bandWidth_cy = 40;
	
	/**
	 * 报警类型及差错类型:广播停播Id
	 */
	public final static Long broadcast_stop = 8016000220L;
	
	/**
	 * 报警类型及差错类型:广播劣播Id
	 */
	public final static Long broadcast_inferior = 8016000210L;
	
	/**
	 * 报警类型及差错类型:电视停播Id
	 */
	public final static Long television_stop = 8016000120L;
	
	/**
	 * 报警类型及差错类型:广电视停播Id
	 */
	public final static Long television_inferior = 8016000110L;

	/**
	 * 差错类型：广播停播
	 */
	public final static String slipType_broadStop = "广播停播";
	
	/**
	 * 差错类型：广播劣播
	 */
	public final static String slipType_broadInferior = "广播劣播";
	
	/**
	 * 差错类型：电视停播
	 */
	public final static String slipType_televStop = "电视停播";
	
	/**
	 * 差错类型：电视劣播
	 */
	public final static String slipType_televnferior = "电视劣播";
	
	/**
	 * 报警级别：1：一级
	 */
	public final static String alarmLevel_one = "1";
	
	/**
	 * 报警级别：2：二级
	 */
	public final static String alarmLevel_two = "2";
	
	/**
	 * 报警级别：3：三级
	 */
	public final static String alarmLevel_three = "3";
	
	/**
	 * 报警级别：4：四级(非差错等级)
	 */
	public final static String alarmLevel_four = "4";
	
	/**
	 * 扫频任务开始频点
	 */
	public final static String startFrequency = "0";
	
	/**
	 * 扫频任务结束频点
	 */
	public final static String endFrequency = "1000";
	
	/**
	 * 扫频任务步长
	 */
	public final static String scanstep = "1";
	
	/**
	 * 编码方式：1：pcm
	 */
	public final static int codeType_pcm = 1;
	
	/**
	 * 编码方式：2：wav
	 */
	public final static int codeType_wav = 2;
	
	/**
	 * 编码方式：3：mp2
	 */
	public final static int codeType_mp2 = 3;
	
	/**
	 * 编码方式：4：mp3
	 */
	public final static int codeType_mp3 = 4;
	
	/**
	 * 编码方式：5：aac
	 */
	public final static int codeType_aac = 5;
	
	/**
	 * 编码方式：1001：mpeg2
	 */
	public final static int codeType_mpeg2 = 1001;
	
	/**
	 * 编码方式：1002：mpeg4
	 */
	public final static Integer codeType_mpeg4 = 1002;
	
	/**
	 * 编码方式：1003：h.264
	 */
	public final static int codeType_h264 = 1003;
	
	/**
	 * SMG前端号电台
	 */
	public final static String SMGDIVEIC1 = "SMG000001";
	
	/**
	 * SMG前端号地球站
	 */
	public final static String SMGDIVEIC2 = "MON000001";
	
	/**
	 * 设备状态0：正常
	 */
	public final static String dcState_0 = "0";
	
	/**
	 * 设备状态1：异常
	 */
	public final static String dcState_1 = "1";
	
	/**
	 * 设备开机状态1: 开机
	 */
	public final static String offStatus_1 = "1";
	
	/**
	 * 报警类别
	 */
	public final static String alarmSort = "alarmSort";
	
	/**
	 * 租户标识:监测中心
	 */
	public final static String tenant_1 = "1";
	
	/**
	 * 租户标识：SMG
	 */
	public final static String tenant_2 = "2";
	
	/**
	 * 租户标识：流动监测
	 */
	public final static String tenant_3 = "3";

	/**
	 * 监测中心二期版本：通用版本
	 */
	public final static String version_0 = "0";
	
	/**
	 * 监测中心二期版本：佛山定制
	 */
	public final static String version_1 = "1";
	
	/**
	 * 系统管理员角色ID值
	 */
	public final static String roleIdOfAdmin = "1";
	
	/**
	 * 默认的运行图播出时间06:00:00
	 */
	public final static String date_defaultRun = "06:00:00";
	
	/**
	 * 重点时段模板0
	 */
	public final static String model_0 = "0";
	
	/**
	 * 差错门限模板1
	 */
	public final static String model_1 = "1";
	
	/**
	 * 报警门限模板2
	 */
	public final static String model_2 = "2";
	
	/**
	 * 运行图每日定时执行时间 定时开关0：开
	 */
	public final static String timingSwitch_0 = "0";
	
	/**
	 * 运行图默认时间是否隔天0：是
	 */
	public final static String isNextDay_0 = "0";
	
	/**
	 * 前端C程序推送的无效值（目前在环境参数中使用）
	 */
	public final static String InvalidValue = "21474836.47";
	
	public final static String InvalidValueOfPower = "214748364.7";
	
	/**
	 * 系统管理员角色ID
	 */
	public final static String ADMIN_ROLEID = "1";
	
	/**
	 * 判断是否进入主页设置查询条件 1：进入
	 */
	public final static String HOMEPAGE_FLAG_1 = "1";
	
	/**
	 * 功能权限和资源权限没有做修改情况的值为-1
	 */
	public final static String QX_NOTMOD_VALUE = "-1";
	
	/**
	 * 不需要用户设置serviceId：FM、AM、SW、SDI、ATV
	 * 数组元素为signalcode
	 */
	public final static String[] signalGroup1 = {"7","9","10","11","12"};
	/**
	 * 音频信号：FM、AM、SW
	 * 数组元素为signalcode
	 */
	public final static String[] AudioSignal = {"10","11","12"};
	
	/**
	 * 频道所属1：广播
	 */
	public final static String sourceFrom_1 = "1";
	
	/**
	 * 频道所属2：广播
	 */
	public final static String sourceFrom_2 = "2";
	
	public final static String[] CHANNEL_HIDDEN_SMG = new String[]{
		// 广播
		"新闻广播AM990", "东广新闻AM1296", "交通广播AM648", "东方都市广播AM792",
		"戏剧曲艺广播AM1197", "中央台中国之声AM540", "中央台经济之声AM855",
		"浦江之声",
		// 电视
		"CCTV-2", "CCTV-3", "CCTV-5", "CCTV-6", "CCTV-7", "CCTV-8", 
		"CCTV-10", "CCTV-11", "CCTV-12", "CCTV-音乐", "CCTV-少儿", "CETV"
	};
	
	/**
	 * 运行图模板类型1：日模板
	 */
	public final static String moduleType_1 = "1";
	
	/**
	 * 运行图模板是否隔天1：隔天
	 */
	public final static String isInterDay_1 = "1";
	
	public final static String[] alarmTimesect = {"重要保障期-重点时段","重要保障期-非重点时段","非重要保障期-重点时段","非重要保障期-非重点时段"};
	
	/**
	 * 语音报警重启
	 */
	public static final String RESET_VOICE_SERVER_DATA_TYPE = "reboot";
	
	/**
	 * 前端离线报警类型
	 */
	public static final Integer MONITOR_ONLINE_ALARTYPE = 1001008;
	
	/**
	 * 设备报警类型码
	 */
	public static final Long  DEVICE_SLIPTYPE = 8016000500L;
	
	/**
	 * 新频率报警类型
	 */
	public static final Integer ALARM_NEW_FREQ = 110002003;
	
	/**
	 * 广播噪声报警类型
	 */
	public static final Integer ALARM_YAWP = 110002008;
	
	/**
	 * 合法 0
	 */
	public static final Integer alarmSureStatus_undefined = 0;
	
	/**
	 * 疑似非法频率 5
	 */
	public static final Integer alarmSureStatus_suspect = 5;
	
	/**
	 * 误报 2
	 */
	public static final Integer alarmSureStatus_false = 2;

	/**
	 * 白名单 3
	 */
	public static final Integer alarmSureStatus_whitelist = 3;

	/**
	 * 异地频率 4
	 */
	public static final Integer alarmSureStatus_offside = 4;
	
	/**
	 * 非法频率 1
	 */
	public static final Integer alarmSureStatus_illegal = 1;
	
	/**
	 * 高清黑边
	 */
	public static final Integer ALARM_HD_BLACKLINE = 7000035;
	
	/**
	 * 高清同播异常
	 */
	public static final Integer ALARM_HD_CASTEXCEPTION = 7000036;
	
	/**
	 * 转播异常
	 */
	public static final Integer ALARM_VIDEO_BROADCAST_ERROR = 7000037;
	
	/**
	 * 响度过高
	 */
	public static final Integer ALARM_VIDEO_LOUD_LEVEL_HIGH = 7000030;
}
