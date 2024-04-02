package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.figure.core.db.JavaMemoryDb;
import com.figure.core.entity.DeviceLiveIndicatorSendStatus;
import com.figure.core.sse.SseEmitterWrapper;
import com.figure.core.sse.SseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.figure.core.db.JavaMemoryDb.ONLY_SEND_CHANGE_VALUE_TO_WEB_CACHE;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_indicator_param_rel")
public class DeviceIndicatorParamRel extends SseMessage implements Serializable {

    public static Integer IN_ALARM =  1;

    public static Integer  NOT_IN_ALARM =  0;

    public static Integer ENABLESETTING_NO = 0;

    public static Integer ENABLESETTING_YES = 1;

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备Id
     */
    @TableField("deviceId")
    private Integer deviceId;


    @TableField(exist = false)
    private String deviceIds;

    /**
     * 设备编号
     */
    @TableField("deviceCode")
    private String deviceCode;

    /**
     * 服务编号
     */
    @TableField(exist = false)
    private String serverCode;

    /**
     * 指标编号
     */
    @TableField("indicatorCode")
    private String indicatorCode;

    /**
     * 指标别名
     */
    @TableField("indicatorAlias")
    private String indicatorAlias;

    /**
     * 实时数据
     */
    @TableField(exist = false)
    private String indicatorValue;

    /**
     * 报警状态
     */
    @TableField(exist = false)
    private Integer alarmStatus;

    /**
     * 启用监测
     */
    @TableField("enableMonitor")
    private Integer enableMonitor;

    /**
     * 监测频率
     */
    @TableField("monitorInterval")
    private String monitorInterval;

    /**
     * 启用报警
     */
    @TableField("enableAlarm")
    private String enableAlarm;

    /**
     * 阈值模板ID
     */
    @TableField("indicatorRuleIds")
    private String indicatorRuleIds;

    /**
     * 交互模式
     */
    @TableField("communicateMode")
    private Integer communicateMode;

    /**
     * 读取交互参数
     */
    @TableField("responseParams")
    private String responseParams;

    /**
     * 读取数据处理
     */
    @TableField("responseParseScript")
    private String responseParseScript;

    /**
     * 启用设置:0否;1是
     */
    @TableField("enableSetting")
    private Integer enableSetting;

    /**
     * 设置交互参数
     */
    @TableField("requestParams")
    private String requestParams;

    /**
     * 设置参数处理
     */
    @TableField("requestParseScript")
    private String requestParseScript;

    /**
     * 校验模式
     */
    @TableField("checkMode")
    private String checkMode;

    /**
     * 当前实时指标值的真实采集时间
     */
    @TableField(exist = false)
    private String collectTime;

    @TableField(exist = false)
    private String updateTime;

    /**
     * 历史数据记录 0：不记录 1：实时记录 2：记录变更
     */
    @TableField("recordMode")
    private Integer recordMode;

    /**
     * 数据变化阈值，只在实时数据变化超过该值时记录
     */
    @TableField("changeThreshold")
    private Integer changeThreshold;

    /**
     * 历史数据保存时长，单位：日
     */
    @TableField("retentionTime")
    private Integer retentionTime;

    /**
     * 指标设置执行类
     */
    @TableField("requestClass")
    private String requestClass;

    /**
     * 指标读取执行类
     */
    @TableField("responseClass")
    private String responseClass;

    /**
     * 模拟量设置上限
     */
    @TableField("setUpperLimit")
    private Double setUpperLimit;

    /**
     * 模拟量设置下限
     */
    @TableField("setLowerLimit")
    private Double setLowerLimit;

    /**
     * 指标数据类型
     */
    @TableField("snmpDataType")
    private String snmpDataType;

    @TableField(exist = false)
    private String deviceName;

    @TableField(exist = false)
    private String indicatorName;

    @TableField(exist = false)
    private String unit;

    @TableField(exist = false)
    private Integer isCritical;

    @Override
    public boolean matchParams(SseEmitterWrapper sseEmitterWrapper) {
        Map<String, Object> params = sseEmitterWrapper.getParams();
        Object deviceIds = params.get("deviceIds");
        Object indicatorCodes = params.get("indicatorCodes");
        Object onlyChange = params.get("onlyChange");

        //web推送间隔
        Object pushInterval = params.get("pushInterval");

        String uuid = sseEmitterWrapper.getUuid();

        boolean result = true;

        if(deviceIds!=null){
            String[] array =  deviceIds.toString().split(",");
            result = java.util.Arrays.asList(array).indexOf(this.deviceId.toString()) != -1;
        }
        if(result && indicatorCodes!=null){
            String[] array =  indicatorCodes.toString().split(",");
            result = java.util.Arrays.asList(array).indexOf(this.indicatorCode) != -1;
        }
        if( result ){
            String key = uuid+"_"+deviceCode+"_"+indicatorCode;
            Long now_st = new Date().getTime();
            result =  JavaMemoryDb.canSend(key,indicatorValue,onlyChange,now_st,this.isCritical,pushInterval);
            if(result){
                ONLY_SEND_CHANGE_VALUE_TO_WEB_CACHE.put(key,new DeviceLiveIndicatorSendStatus(indicatorValue,now_st));
            }
        }
        return result;
    }
}
