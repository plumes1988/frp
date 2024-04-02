package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.figure.core.sse.SseEmitterWrapper;
import com.figure.core.sse.SseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 应用服务报警记录表
 * </p>
 *
 * @author jobob
 * @since 2024-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_service_alarm_message")
public class DeviceServiceAlarmMessage extends SseMessage implements Serializable {

    /*
    / 报警类型 start
     */

    public static Integer ALARMTYPE_OFFLINE = 1;

    public static String ALARMTYPE_OFFLINE_CONTENT = "未接受到心跳包，判定采集服务已经离线";

    public static Integer ALARMTYPE_EXCEPTION = 2;

    /*
    / 报警类型 end
     */

    public static String UN_CONFIRMED = "0";

    public static String CONFIRMED  = "1";

    public static String MISINTERPRET  = "2";

    public static final Map<String,String> confirm_map = new HashMap(){{
        put(UN_CONFIRMED,"未确认");
        put(CONFIRMED,"已确认");
        put(MISINTERPRET,"确认误报");
    }};

    /*
    / 服务类型 start
     */
    public static Integer SERVICE_TYPE_COLLECT = 1; //采集
    public static Integer SERVICE_TYPE_SPECTRUM_MONITOR = 2; //频谱监测
    public static Integer SERVICE_TYPE_REDIS = 3; //redis
    public static Integer SERVICE_TYPE_ROCKTMQ = 4; //rocketmq
    public static Integer SERVICE_TYPE_PLATFORM = 5; //网管平台
    public static Integer SERVICE_TYPE_MYSQL = 6; // mysql
    /*
    / 服务类型 end
     */

    public static String NOT_END = "0";

    public static String ENDED = "1";

    public static final Map<String,String> status_map = new HashMap(){{
        put(NOT_END,"未结束");
        put(ENDED,"未结束");
    }};

    /**
     * 报警ID
     */
    @TableId("alarmId")
    private Long alarmId;

    /**
     * 服务编号
     */
    @TableField("serviceCode")
    private String serviceCode;

    /**
     * 服务名称
     */
    @TableField("serviceName")
    private String serviceName;

    /**
     * 服务类型：1:采集服务 2:频谱监测服务
     */
    @TableField("serviceType")
    private Integer serviceType;

    /**
     * 报警类型 1、服务离线  2、服务运行异常
     */
    @TableField("alarmType")
    private Integer alarmType;

    /**
     * 报警信息
     */
    @TableField("alarmMsg")
    private String alarmMsg;

    /**
     * 报警开始时间
     */
    @TableField("happenTime")
    private Date happenTime;

    /**
     * 报警结束时间
     */
    @TableField("endTime")
    private Date endTime;

    /**
     * 报警持续时间
     */
    @TableField("continueTime")
    private Integer continueTime;

    /**
     * 报警状态
     */
    @TableField("status")
    private String status;

    /**
     * 确认状态 0未确认 1确认报警 2确认误报
     */
    @TableField("confirm")
    private String confirm;

    /**
     * 处理结果
     */
    @TableField("result")
    private String result;


    @Override
    public boolean matchParams(SseEmitterWrapper sseEmitterWrapper) {
        return true;
    }
}
