package com.figure.core.model.multiview;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 多画面服务
 * </p>
 *
 * @author jobob
 * @since 2021-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_service_info")
public class MultiviewServiceInfo extends BaseModel implements Serializable {


    /**
     * 多画面服务ID
     */
    @TableId(value = "multiviewServiceId", type = IdType.AUTO)
    private Integer multiviewServiceId;

    /**
     * 多画面服务名称
     */
    @TableField("multiviewServiceName")
    private String multiviewServiceName;

    /**
     * 屏幕扩展类型：单屏:1,双屏:2,四屏4
     */
    @TableField("screenType")
    private Integer screenType;

    /**
     * 前端Id
     */
    @TableField("frontId")
    private Integer frontId;

    /**
     * 服务设备ID
     */
    @TableField("deviceId")
    private Integer deviceId;

    /**
     * 服务管理IP
     */
    @TableField("multiviewServiceIP")
    private String multiviewServiceIP;

    /**
     * 资源数
     */
    @TableField("multiviewTunnelNum")
    private Integer multiviewTunnelNum;

    /**
     * 是否使用GPU：0不用，1使用
     */
    @TableField("useGPU")
    private Integer useGPU;

    /**
     * 多画面类型:1普通多画面、2报警触发多画面、3比对多画面
     */
    @TableField("multiviewServiceType")
    private Integer multiviewServiceType;

    /**
     * 音频跳表显示模式：0-右边，1-左边，2-不显示
     */
    @TableField("audioPosition")
    private Integer audioPosition;

    /**
     * 从采集站获取报警：0 否；1 是
     */
    @TableField("isAlarmFromMonitor")
    private Integer isAlarmFromMonitor;

    /**
     * 0-不可调度，1-可调度
     */
    @TableField("schedulable")
    private Integer schedulable;

    /**
     * 多画面下发配置文件
     */
    @TableField("config")
    private String config;

    /**
     * 同步状态 0：未同步 1：已同步 
     */
    @TableField("syncStatus")
    private Integer syncStatus;

    /**
     * 运行状态
     */
    @TableField("runStatus")
    private Integer runStatus;

    /**
     * 创建人员ID
     */
    @TableField("createUserId")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新人员ID
     */
    @TableField("updateUserId")
    private Integer updateUserId;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;


    /**
     * 第几列
     */
    @TableField(exist = false)
    private Integer xPos;

    /**
     * 第几行
     */
    @TableField(exist = false)
    private Integer yPos;

    /**
     * 第几行
     */
    @TableField(exist = false)
    private Integer multiviewScreenPos;

    /**
     * 第几行
     */
    @TableField(exist = false)
    private Integer screenId;

}
