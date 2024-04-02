package com.figure.core.model.audit;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 节目播单表
 * </p>
 *
 * @author jobob
 * @since 2021-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("audit_program_schedule")
public class AuditProgramSchedule implements Serializable {

    /**
     * 唯一标识、无业务含义、便于curd
     */
    @TableId(value="id",type= IdType.AUTO)
    private String id;

    /**
     * 播单ID
     */
    @TableField("scheduleId")
    private String scheduleId;

    /**
     * 播单版本
     */
    @TableField("scheduleVersion")
    private String scheduleVersion;

    /**
     * 播单类型
     */
    @TableField("scheduleType")
    private String scheduleType;

    /**
     * 频道
     */
    @TableField("channelCode")
    private String channelCode;

    /**
     * 节目单中元素ID
     */
    @TableField("itemID")
    private String itemID;

    /**
     * 播出序号
     */
    @TableField("itemIndex")
    private Integer itemIndex;

    /**
     * 播出开始时间
     */
    @TableField("playTime")
    private Date playTime;

    /**
     * 播出时长
     */
    @TableField("playDuration")
    private Integer playDuration;

    /**
     * 关联素材
     */
    @TableField("clipId")
    private Integer clipId;

    /**
     * 关联名称
     */
    @TableField(exist = false)
    private String clipName;

    /**
     * 入点位置
     */
    @TableField("trimIn")
    private Integer trimIn;

    /**
     * 栏目编号
     */
    @TableField("columnCode")
    private String columnCode;

    /**
     * 栏目名称
     */
    @TableField("columnName")
    private String columnName;

    /**
     * 节目编号
     */
    @TableField("programCode")
    private String programCode;

    /**
     * 节目名称
     */
    @TableField("programName")
    private String programName;

    /**
     * 视频格式
     */
    @TableField("video")
    private Integer video;

    /**
     * AFD信息
     */
    @TableField("AFD")
    private Integer AFD;

    /**
     * 音频声道信息
     */
    @TableField("audio")
    private Integer audio;

    /**
     * 播送方式
     */
    @TableField("throughType")
    private Integer throughType;

    /**
     * 是否新闻
     */
    @TableField("newsFlag")
    private Integer newsFlag;

    /**
     * 是否广告
     */
    @TableField("isAD")
    private Integer isAD;

    /**
     * 是否直播
     */
    @TableField("isLive")
    private Integer isLive;

    /**
     * 节目状态
     */
    @TableField("playItemReadyStatus")
    private Integer playItemReadyStatus;

    /**
     * 主播出设备
     */
    @TableField("devIDM")
    private Integer devIDM;

    /**
     * 备播出设备
     */
    @TableField("devIDB")
    private Integer devIDB;

    /**
     * 是否需要包装
     */
    @TableField("isChannelBranding")
    private Integer isChannelBranding;

    /**
     * 包装项目代码
     */
    @TableField("channelPackingItemCode")
    private String channelPackingItemCode;

    /**
     * 节目单唯一编号
     */
    @TableField("broadcastListItemID")
    private String broadcastListItemID;

    /**
     * 节目单日期
     */
    @TableField("playDate")
    private String playDate;

    /**
     * 节目结束时间
     */
    @TableField("playEndTime")
    private LocalDateTime playEndTime;

    /**
     * 播出事件：0：R 定时，1：D 顺序，2：U 不定时
     */
    @TableField("playEvent")
    private Integer playEvent;

    /**
     * 当前节目上一条最近的U事件节目ItemId
     */
    @TableField("refUBroadcastItemId")
    private String refUBroadcastItemId;

    /**
     * 播出设备类型：0视频服务器、1磁带、2蓝光、3P2、4移动介质、5直播信号

     */
    @TableField("devTypeCode")
    private Integer devTypeCode;

    /**
     * 直播信源锁定，0未锁定，1锁定
     */
    @TableField("liveLockStatus")
    private Integer liveLockStatus;




    @TableField(exist = false)
    private String playDate_str;

    @TableField(exist = false)
    private String playTime_str;
    /**
     * 语言
     */
    @TableField(exist = false)
    private String language;

    public String getPlayDate_str() {
        return DateUtil.format(this.playTime,"yyyy-MM-dd");
    }

    public String getPlayTime_str() {
        return DateUtil.format(this.playTime,"HH:mm:ss");
    }

    @TableField(exist = false)
    List<AuditProgramSchedule> items = new ArrayList<>();
}
