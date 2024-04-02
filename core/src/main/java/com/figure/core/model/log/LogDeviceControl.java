package com.figure.core.model.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 设备操作日志
 * </p>
 *
 * @author jobob
 * @since 2023-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("log_device_control")
public class LogDeviceControl extends SseMessage implements Serializable {

    public static Integer SETTING = 0;

    public static Integer NORMAL = 1;

    public static Integer EXCEPTION  = 2;

    public static Integer TIMEOUT = 3;

    public static Integer PARAMETER_EXCEPTION = 4;

    public static final Map<Integer,String> requestStatus_map = new HashMap(){{
        put(SETTING,"设置中");
        put(NORMAL,"正常");
        put(EXCEPTION,"异常");
        put(TIMEOUT,"超时");
        put(PARAMETER_EXCEPTION,"参数错误");
    }};

    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    /**
     * 请求ID
     */
    @TableField("requestId")
    private String requestId;

    /**
     * 请求时间
     */
    @TableField("requestTime")
    private Date requestTime;

    /**
     * 设备编号
     */
    @TableField("deviceCode")
    private String deviceCode;

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
     * 指标编号
     */
    @TableField("indicatorCode")
    private String indicatorCode;

    /**
     * 采集服务编号
     */
    @TableField("serverCode")
    private String serverCode;

    /**
     * 指标值
     */
    @TableField("indicatorValue")
    private String indicatorValue;

    /**
     * 操作人员
     */
    @TableField("operatorUserId")
    private Integer operatorUserId;

    /**
     * 响应消息
     */
    @TableField("responseContent")
    private String responseContent;

    /**
     * 响应时间
     */
    @TableField("responseTime")
    private Date responseTime;

    /**
     ** 0:未响应 1:正常 2:异常 3:超时
     */
    @TableField("requestStatus")
    private Integer requestStatus;


    @TableField(exist = false)
    private String deviceName;

    @TableField(exist = false)
    private String indicatorName;

    @TableField(exist = false)
    private String operatorUserName;

    @TableField(exist = false)
    private Integer deviceId;

    @Override
    public boolean matchParams(SseEmitterWrapper sseEmitterWrapper) {
        return true;
    }
}
