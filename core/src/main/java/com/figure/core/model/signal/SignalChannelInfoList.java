package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("signal_channel_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalChannelInfo频道信息", description = "频道信息")
public class SignalChannelInfoList extends BaseModel {
    /**
     * 频道ID
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty("频道ID")
    private String channelId;
    /**
     * 频道名称
     */
    @ApiModelProperty("频道名称")
    private String channelName;
    /**
     * 频道别名
     */
    @ApiModelProperty("频道别名")
    private String channelAlias;
    /**
     * 码率
     */
    @ApiModelProperty("码率")
    private Integer bitRate;
    /**
     * 频率ID
     */
    @ApiModelProperty("频率ID")
    private String frequencyId;
    /**
     * 监测站ID
     */
    @ApiModelProperty("监测站ID")
    private Integer frontId;
    /**
     * 监测点ID
     */
    @ApiModelProperty("监测点ID")
    private Integer logicPositionId;
    /**
     * 通道Id
     */
    @ApiModelProperty("通道Id")
    private Integer interfaceId;
    /**
     * 信号类型编号
     */
    @ApiModelProperty("信号类型编号")
    private Integer signalCode;
    /**
     * 业务标识
     */
    @ApiModelProperty("业务标识")
    private Integer serviceId;
    /**
     * 关联标准频道code
     */
    @ApiModelProperty("关联标准频道code")
    private String logicChannelCode;
    /**
     * 音频PID
     */
    @ApiModelProperty("音频PID")
    private Integer audioPid;
    /**
     * 视频PID
     */
    @ApiModelProperty("视频PID")
    private Integer videoPid;
    /**
     * 是否收费：1收费；0公益
     */
    @ApiModelProperty("是否收费：1收费；0公益")
    private Integer isCharge;
    /**
     * 是否高清类型:0SD、1HD、2 4k
     */
    @ApiModelProperty("是否高清类型:0SD、1HD、2 4k")
    private Integer videoType;
    /**
     * 授权：0授权、1未授权
     */
    @ApiModelProperty("授权：0授权、1未授权")
    private Integer isEncrypt;
    /**
     * 来自：1广播；2电视
     */
    @ApiModelProperty("来自：1广播；2电视")
    private Integer mediaType;
    /**
     * 频道信号来源，例如URL或者HK摄像头
     */
    @ApiModelProperty("频道信号来源，例如URL或者HK摄像头")
    private String channelSource;
    /**
     * 上传的频道图片http路径
     */
    @ApiModelProperty("上传的频道图片http路径")
    private String channelImgUrl;
    /**
     * 同步标识 0：合法 1：非法   (给前端使用)
     */
    @ApiModelProperty("同步标识 0：合法 1：非法   (给前端使用)")
    private Integer channelFlag;
    /**
     * 同步状态 0：已同步 1：未同步 2:新频 3:待删除 4:已删除
     */
    @ApiModelProperty("同步状态 0：已同步 1：未同步 2:新频 3:待删除 4:已删除")
    private Integer syncStatus;

    /**
     * 直播监看地址
     */
    @ApiModelProperty("直播监看地址")
    private String webURL;

    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    private String frontName;

    private String logicPositionName;

    private String frequencyName;

    private String logicChannelCodeName;
}
