package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_alarm_message")
public class DeviceAlarmMessage extends SseMessage implements Serializable {

    public static String NOT_END = "0";

    public static String ENDED = "1";

    public static final Map<String,String> status_map = new HashMap(){{
        put(NOT_END,"未结束");
        put(ENDED,"未结束");
    }};

    public static String UN_CONFIRMED = "0";

    public static String CONFIRMED  = "1";

    public static String MISINTERPRET  = "2";

    public static final Map<String,String> confirm_map = new HashMap(){{
        put(UN_CONFIRMED,"未确认");
        put(CONFIRMED,"已确认");
        put(MISINTERPRET,"确认误报");
    }};

    public static Integer INDICATOR_ANOMALY = 1;

    public static Integer PARAMETER_ANOMALY  = 2;

    public static Integer COMMUNICATION_ANOMALY  = 3;

    public static Integer CONTROL_FAILURE  = 4;

    public static final Map<Integer,String> faultType_map = new HashMap(){{
        put(INDICATOR_ANOMALY,"指标异常");
        put(PARAMETER_ANOMALY,"参数异常");
        put(COMMUNICATION_ANOMALY,"通讯异常");
        put(CONTROL_FAILURE,"控制失效");
    }};

    public static String OFFLINE = "0";

    public static String ONLINE = "1";

    /**
     * 报警ID
     */
    //@TableId(value = "alarmId", type = IdType.AUTO)
    @TableId(value = "alarmId")
    private Long alarmId;

    /**
     * 前端ID
     */
    @TableField("frontId")
    private Integer frontId;

    /**
     * 前端名称
     */
    @TableField(exist = false)
    private String frontName;

    /**
     * 设备编号
     */
    @TableField("deviceCode")
    private String deviceCode;

    /**
     * 指标编号
     */
    @TableField("indicatorCode")
    private String indicatorCode;

    /**
     * 报警类型ID
     */
    @TableField("alarmTypeId")
    private Integer alarmTypeId;

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
     * 确认状态
     */
    @TableField("confirm")
    private String confirm;

    /**
     * 处理结果
     */
    @TableField("result")
    private String result;

    /**
     * 处理结果
     */
    @TableField("faultType")
    private Integer faultType;

    @TableField(exist = false)
    private Integer deviceId;

    @TableField(exist = false)
    private String deviceName;

    @TableField(exist = false)
    private String indicatorName;

    @TableField(exist = false)
    private String alarmTypeName;

    @TableField(exist = false)
    private String alarmType;

    @Override
    public boolean matchParams(SseEmitterWrapper sseEmitterWrapper) {
        Map<String, Object> params = sseEmitterWrapper.getParams();
        Object deviceIds = params.get("deviceIds");
        Object indicatorCodes = params.get("indicatorCodes");
        boolean result = true;
        if(deviceIds!=null && this.deviceId !=null){
            String[] array =  deviceIds.toString().split(",");
            result = java.util.Arrays.asList(array).indexOf(this.deviceId.toString()) != -1;
        }
        if(result && indicatorCodes!=null){
            String[] array =  indicatorCodes.toString().split(",");
            result = java.util.Arrays.asList(array).indexOf(this.indicatorCode) != -1;
        }
        return result;
    }

    @TableField(exist = false)
    private Integer notifyFlag = 1;

    @TableField(exist = false)
    private Integer isDelete = 0;

    @TableField(exist = false)
    private Integer notEndToEnded = 0;

    @TableField(exist = false)
    private Integer alarmUpdateType;
}
