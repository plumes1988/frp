package com.figure.core.query.signal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.figure.core.model.signal.SignalEpgInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Ge;
import com.figure.core.util.copycat.annotaion.Le;
import com.figure.core.util.copycat.annotaion.Like;
import com.figure.core.util.copycat.query.AbstractQuery;
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
 * @author feather
 * @date 2021-05-25 17:10:38
 */
@Data
@Accessors(chain = true)
@TableName("signal_epg_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalEpgInfo节目单信息", description = "节目单信息")
public class SignalEpgInfoQuery extends AbstractQuery<SignalEpgInfo> {

    /**
     * EPG版本号
     */
    @Like
    @ApiModelProperty("EPG版本号")
    private String epgVersion;
    /**
     * 逻辑频道ID
     */
    @Eq
    @ApiModelProperty("逻辑频道ID")
    private Long logicChannelId;
    /**
     * 节目编号
     */
    @Eq
    @ApiModelProperty("节目编号")
    private String programCode;
    /**
     * 节目名称
     */
    @Eq
    @ApiModelProperty("节目名称")
    private String programName;
    /**
     * 节目类型
     */
    @Eq
    @ApiModelProperty("节目类型")
    private String programType;
    /**
     * 开始日期
     */
    @Ge
    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 开始时间
     */
    @Le
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date startTime;

    @Eq(alias = "startDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDateEqual;
    /**
     * 节目时长 单位秒
     */
    @Eq
    @ApiModelProperty("节目时长 单位秒")
    private Long programLen;
    /**
     * 频道ID
     */
    @Eq
    @ApiModelProperty("频道ID")
    private String channelId;
    /**
     * 信号类型编号
     */
    @Eq
    @ApiModelProperty("信号类型编号")
    private String signalCode;
    /**
     * 源地址
     */
    @Eq
    @ApiModelProperty("源地址")
    private String programSource;
    /**
     * 是否可用 0：可用，1：不可用
     */
    @Eq
    @ApiModelProperty("是否可用 0：可用，1：不可用")
    private String vaild;
    @Eq
    /** 审核状态 未审核 0，申请审核 1，通过 2，退回 3 */
    @ApiModelProperty("审核状态 未审核 0，申请审核 1，通过 2，退回 3")
    private Long auditStatus;
    /**
     * 0-否，1-是
     */
    @Eq
    @ApiModelProperty("0-否，1-是")
    private Long isVersionBak;

}