package com.figure.core.model.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.figure.core.config.CustomTimeSerializer;
import com.figure.core.rocketmq.struct.consumer.DeviceStateIndicatorChangeP2SConsumer;
import com.figure.core.sse.Constants;
import com.figure.core.sse.SseEmitterWrapper;
import com.figure.core.sse.SseMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 设备状态直播变更日志
 * </p>
 *
 * @author feather
 * @date 2024-01-17 14:21:47
 */
@Data
@Accessors(chain = true)
@TableName("log_device_state_indicator_change")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogDeviceStateIndicatorChange", description = "设备状态直播变更日志")
public class LogDeviceStateIndicatorChange extends SseMessage {

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("")
    private Integer id;
    /**
     * 前端站点ID
     */
    @ApiModelProperty("前端站点ID")
    private Integer frontId;
    /**
     * 设备编号
     */
    @ApiModelProperty("设备编号")
    private String deviceCode;
    /**
     * 指标编号
     */
    @ApiModelProperty("指标编号")
    private String indicatorCode;
    /**
     * 旧值
     */
    @ApiModelProperty("旧值")
    private String oldIndicatorValue;
    /**
     * 新值
     */
    @ApiModelProperty("新值")
    private String newIndicatorValue;
    /**
     * 变更时间
     */
    @ApiModelProperty("变更时间")
    @JsonSerialize(using = CustomTimeSerializer.class) // 应用自定义序列化器
    private Date changeTime;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;


    public LogDeviceStateIndicatorChange(){
        super();
    };


    public LogDeviceStateIndicatorChange(DeviceStateIndicatorChangeP2SConsumer consumer, Integer frontId) {
        this.frontId = frontId;
        this.deviceCode = consumer.getDeviceCode();
        this.indicatorCode = consumer.getIndicatorCode();
        this.oldIndicatorValue = consumer.getOldIndicatorValue();
        this.newIndicatorValue = consumer.getNewIndicatorValue();
        this.changeTime = consumer.getChangeTime();
    }


    /**
     * 设备Id
     */
    @TableField(exist = false)
    private Integer deviceId;

    @Override
    public String getTopic() {
        return Constants.LOG_DEVICE_STATE_INDICATOR_CHANGE;
    }

    @Override
    public boolean matchParams(SseEmitterWrapper sseEmitterWrapper) {
        return true;
    }


    /**
     * 前端站点名称
     */
    @TableField(exist = false)
    private String frontName;

    /**
     * 设备名称
     */
    @TableField(exist = false)
    private String deviceName;


    /**
     * 指标名称
     */
    @TableField(exist = false)
    private String indicatorName;
}