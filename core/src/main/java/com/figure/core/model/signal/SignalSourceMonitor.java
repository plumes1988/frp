package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 逻辑频道监测规则
 * </p>
 *
 * @author jobob
 * @since 2022-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("signal_source_monitor")
public class SignalSourceMonitor implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联逻辑频道编号
     */
    @TableField("logicChannelCode")
    private String logicChannelCode;

    /**
     * 信源调度服务编号
     */
    @TableField("sourceServiceCode")
    private String sourceServiceCode;

    /**
     * 信源调度模式0：常规，1：强制主源，2：强制备源
     */
    @TableField("sourceChangeMode")
    private Integer sourceChangeMode;

    /**
     * 关联智能安播报警阈值规则
     */
    @TableField("alarmThresholdModel")
    private Integer alarmThresholdModel;

    /**
     * 是否开启预监测：0：禁用、1：启用
     */
    @TableField("preMonitorEnable")
    private Integer preMonitorEnable;

    /**
     * 预监测1阶段开始时间，提前多少秒
     */
    @TableField("pre1stTime")
    private Integer pre1stTime;

    /**
     * 预监测1阶段报警阈值规则
     */
    @TableField("pre1stAlarmThresholdModel")
    private Integer pre1stAlarmThresholdModel;

    /**
     * 预监测2阶开始时间，提前多少秒，少于1阶段
     */
    @TableField("pre2stTime")
    private Integer pre2stTime;

    /**
     * 预监测2阶报警阈值规则
     */
    @TableField("pre2stAlarmThresholdModel")
    private Integer pre2stAlarmThresholdModel;

    /**
     * 监测开关
     */
    @TableField("monitorEnable")
    private Integer monitorEnable;

    /**
     * 关联节目单频道编号
     */
    @TableField("programListChannelCode")
    private String programListChannelCode;

    /**
     * 节目单采集开关
     */
    @TableField("collectProgramListEnable")
    private Integer collectProgramListEnable;


    /**
     * 测试图节目监测开关
     */
    @TableField("testProgramMonitorEnable")
    private Integer testProgramMonitorEnable;

    /**
     * 测试图节目编号
     */
    @TableField("testProgramCode")
    private String testProgramCode;

    /**
     * 无节目单音频格式配置
     */
    @TableField("noProgramListAudioFormat")
    private Integer noProgramListAudioFormat;
}
