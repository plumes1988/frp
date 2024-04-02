package com.figure.core.model.spectrum;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import com.figure.core.helper.DateHelper;
import com.figure.core.rocketmq.struct.consumer.ServiceStatusS2PConsumer;
import com.figure.core.rocketmq.struct.info.SpectrumServiceAlarmInfo;
import com.figure.core.rocketmq.struct.info.SpectrumServiceTaskInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 频谱服务报警记录
 * </p>
 *
 *@author feather
 *@date 2024-03-06 17:19:49
 */
@Data
@Accessors(chain = true)
@TableName("spectrum_service_alarm_message")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SpectrumServiceAlarmMessage", description = "频谱服务报警记录")
public class SpectrumServiceAlarmMessage extends BaseModel {

    /** 
     *主键 
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long alarmId;
    /** 
     *1服务报警2任务报警 
     */
    @ApiModelProperty("1服务报警2任务报警")
    private Integer alarmClass;
    /** 
     *频谱服务编号 
     */
    @ApiModelProperty("频谱服务编号")
    private String serviceCode;
    /** 
     *频谱服务名称 
     */
    @ApiModelProperty("频谱服务名称")
    private String serviceName;
    /** 
     *频谱服务和频谱仪关联ID 
     */
    @ApiModelProperty("频谱服务和频谱仪关联ID")
    private Integer relId;
    /** 
     *频谱仪设备编号 
     */
    @ApiModelProperty("频谱仪设备编号")
    private String spectrumCode;
    /** 
     *频谱仪设备名称 
     */
    @ApiModelProperty("频谱仪设备名称")
    private String spectrumName;
    /** 
     *报警类型 
     */
    @ApiModelProperty("报警类型")
    private Integer alarmType;
    /** 
     *报警类型名称 
     */
    @ApiModelProperty("报警类型名称")
    private String alarmTypeName;
    /** 
     *报警开始时间 
     */
    @ApiModelProperty("报警开始时间")
    private Date startTime;
    /**
     *报警结束时间
     */
    @ApiModelProperty("报警结束时间")
    private Date endTime;
    /**
     *最后更新时间
     */
    @ApiModelProperty("最后更新时间")
    private Date lastUpdateTime;
    /** 
     *报警时长 
     */
    @ApiModelProperty("报警时长")
    private Integer duration;
    /** 
     *0恢复1报警中 
     */
    @ApiModelProperty("0恢复1报警中")
    private Integer alarmFlag;
    /**
     *报警信息 
     */
    @ApiModelProperty("报警信息")
    private String alarmContent;
    /**
     * 处理状态 0未确认 1事故 2误报
     */
    @ApiModelProperty("处理状态 0未确认 1事故 2误报")
    private Integer commitFlag;
    /**
     * 处理意见
     */
    @ApiModelProperty("处理意见")
    private String commitInfo;
    /** 
     *状态：0:未删除、1:已删除、2:停用 
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;


    public SpectrumServiceAlarmMessage(ServiceStatusS2PConsumer consumer, SpectrumServiceAlarmInfo spectrumServiceAlarmInfo,String serviceName, String alarmTypeName) {
        this.alarmClass = 1;
        this.serviceCode = consumer.getServiceCode();
        this.serviceName = serviceName;
        this.alarmType = spectrumServiceAlarmInfo.getAlarmType();
        this.alarmTypeName = alarmTypeName;
        this.startTime = DateHelper.parse(spectrumServiceAlarmInfo.getStartTime());
        this.duration = spectrumServiceAlarmInfo.getDuration();
        this.endTime = DateHelper.add(this.startTime, Calendar.SECOND,this.duration);
        this.lastUpdateTime = new Date();
        this.alarmFlag = spectrumServiceAlarmInfo.getAlarmFlag();
        this.alarmContent = spectrumServiceAlarmInfo.getAlarmContent();
        this.isDelete = 0;
    }

    public SpectrumServiceAlarmMessage(ServiceStatusS2PConsumer consumer, SpectrumServiceTaskInfo spectrumServiceTaskInfo ,
                                       SpectrumServiceAlarmInfo spectrumServiceAlarmInfo, String serviceName, String spectrumName, String alarmTypeName) {
        this.alarmClass = 2;
        this.serviceCode = consumer.getServiceCode();
        this.serviceName = serviceName;
        this.relId = spectrumServiceTaskInfo.getTaskID();
        this.spectrumCode = spectrumServiceTaskInfo.getObjectID();
        this.alarmType = spectrumServiceAlarmInfo.getAlarmType();
        this.alarmTypeName = alarmTypeName;
        this.spectrumName = spectrumName;
        this.startTime = DateHelper.parse(spectrumServiceAlarmInfo.getStartTime());
        this.duration = spectrumServiceAlarmInfo.getDuration();
        this.endTime = DateHelper.add(this.startTime, Calendar.SECOND,this.duration);
        this.lastUpdateTime = new Date();
        this.alarmFlag = spectrumServiceAlarmInfo.getAlarmFlag();
        this.alarmContent = spectrumServiceAlarmInfo.getAlarmContent();
        this.isDelete = 0;
    }

    public SpectrumServiceAlarmMessage() {

    }
}