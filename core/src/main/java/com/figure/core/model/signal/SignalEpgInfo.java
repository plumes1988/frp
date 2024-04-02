package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 节目单信息
 * </p>
 *
 *@author feather
 *@date 2021-05-25 17:10:38
 */
@Data
@Accessors(chain = true)
@TableName("signal_epg_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalEpgInfo节目单信息", description = "节目单信息")
public class SignalEpgInfo extends BaseModel {

    /** 节目单ID */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("节目单ID")
    private Long epgId;
    /**
     * EPG版本号
     */
    @ApiModelProperty("EPG版本号")
    private String epgVersion;
    /**
     * 逻辑频道ID
     */
    @ApiModelProperty("逻辑频道ID")
    private String logicChannelId;
    /**
     * 节目编号
     */
    @ApiModelProperty("节目编号")
    private String programCode;
    /** 节目名称 */
    @ApiModelProperty("节目名称")
    private String programName;
    /** 节目类型 */
    @ApiModelProperty("节目类型")
    private String programType;
    /** 开始日期 */
    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /** 开始时间 */
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date startTime;
    /** 节目时长 单位秒 */
    @ApiModelProperty("节目时长 单位秒")
    private Long programLen;
    /** 频道ID */
    @ApiModelProperty("频道ID")
    private String channelId;
    /** 信号类型编号 */
    @ApiModelProperty("信号类型编号")
    private String signalCode;
    /** 源地址 */
    @ApiModelProperty("源地址")
    private String programSource;
    /** 是否可用 0：可用，1：不可用 */
    @ApiModelProperty("是否可用 0：可用，1：不可用")
    private String vaild;
    /** 审核状态 未审核 0，申请审核 1，通过 2，退回 3 */
    @ApiModelProperty("审核状态 未审核 0，申请审核 1，通过 2，退回 3")
    private Long auditStatus;
    /** 0-否，1-是 */
    @ApiModelProperty("0-否，1-是")
    private Long isVersionBak;

}