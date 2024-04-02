package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.util.copycat.annotaion.In;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.figure.core.base.BaseModel;


/**
 * <p>
 * 信号接口信息
 * </p>
 *
 *@author feather
 *@date 2021-05-25 17:10:38
 */
@Data
@Accessors(chain = true)
@TableName("signal_interface_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalInterfaceInfo信号接口信息", description = "信号接口信息")
public class SignalInterfaceInfo {

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("")
    private Integer interfaceId;
    /**
     * 前端ID
     */
    @ApiModelProperty("前端ID")
    private Integer frontId;
    /**
     * 关联设备ID
     */
    @ApiModelProperty("关联设备ID")
    private Integer deviceId;
    /**
     * 信号类型
     */
    @ApiModelProperty("信号类型")
    private Integer signalCode;
    /**
     * 接口IP
     */
    @ApiModelProperty("接口IP")
    private String interfaceIp;
    /**
     * 接口号
     */
    @ApiModelProperty("接口号")
    private Integer interfaceNumber;
    /**
     * 通道序号，同一前端，同一信号类型从1开始计数
     */
    @ApiModelProperty("通道序号，同一前端，同一信号类型从1开始计数")
    private Integer serialNumber;
    /**
     * 最大转发数目
     */
    @ApiModelProperty("最大转发数目")
    private Integer maxStreamCount;
    /**
     * 接口用途。1，通用可抢占资源；2，扫频专用资源；4，绑定频点资源；5，实时视频专用；6，轮播专用；7，录制专用；8，指标专用
     */
    @ApiModelProperty("接口用途。1，通用可抢占资源；2，扫频专用资源；4，绑定频点资源；5，实时视频专用；6，轮播专用；7，录制专用；8，指标专用")
    private Integer workMode;
    /**
     * 绑定采集点
     */
    @ApiModelProperty("绑定采集点")
    private Integer logicPositionId;
    /**
     * 绑定频率
     */
    @ApiModelProperty("绑定频率")
    private String sourceId;
    /** 同步状态 0：已同步 1：未同步 */
    @ApiModelProperty("同步状态 0：已同步 1：未同步")
    private String syncStatus;

}