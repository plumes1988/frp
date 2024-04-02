package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 逻辑频道管理
 * </p>
 *
 * @author jobob
 * @since 2021-05-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("signal_logic_channel")
public class SignalLogicChannel extends BaseModel {

    /**
     * 逻辑频道编号
     */
    @TableId(type = IdType.AUTO)
    private Integer logicChannelId;

    /**
     * 逻辑频道编号
     */
    @TableField("channelCode")
    private String channelCode;

    /**
     * 频道名称
     */
    @TableField("channelName")
    private String channelName;

    /**
     * 级别 1 央视， 2 省级,  3 地市,  4 区县，5其他
     */
    @TableField("channelGrade")
    private String channelGrade;

    /**
     * 播出机构id
     */
    @TableField("agencyId")
    private Integer agencyId;

    /**
     * 0 非卫视, 1 卫视
     */
    @TableField("isStatellite")
    private String isStatellite;

    /**
     * 0 非本地, 1 本地
     */
    @TableField("isLocal")
    private String isLocal;

    /**
     * 媒体类型 1 音频，2 视频
     */
    @TableField("mediaType")
    private String mediaType;

    /**
     * 频道图片存储路径
     */
    @TableField("channelImgUrl")
    private String channelImgUrl;

    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    @TableField("isDelete")
    private Integer isDelete;

    @TableField(exist = false)
    private List<Integer> chainIds = new ArrayList<>();

    @TableField(exist = false)
    private SignalSourceMonitor signalSourceMonitor;
}
