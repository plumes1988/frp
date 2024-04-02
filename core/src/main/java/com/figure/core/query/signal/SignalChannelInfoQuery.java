package com.figure.core.query.signal;

import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.In;
import com.figure.core.util.copycat.annotaion.Like;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * <p>
 * 频道信息
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:37
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalChannelInfo频道信息", description = "频道信息")
public class SignalChannelInfoQuery extends AbstractQuery<SignalChannelInfo> {

    /**
     * 频道名称
     */
    @Like
    @ApiModelProperty("频道名称")
    private String channelName;
    /**
     * 频道别名
     */
    @Like
    @ApiModelProperty("频道别名")
    private String channelAlias;
    /**
     * 频率ID
     */
    @Eq
    @ApiModelProperty("频率ID")
    private String frequencyId;
    /**
     * 监测站ID
     */
    @Eq
    @ApiModelProperty("监测站ID")
    private Integer frontId;
    /**
     * 码率
     */
    @Eq
    @ApiModelProperty("码率")
    private Integer bitRate;
    /**
     * 监测点ID
     */
    @Eq
    @ApiModelProperty("监测点ID")
    private Integer logicPositionId;
    /**
     * 通道Id
     */
    @Eq
    @ApiModelProperty("通道Id")
    private Integer interfaceId;
    /**
     * 信号类型编号
     */
    @Eq
    @ApiModelProperty("信号类型编号")
    private String signalCode;
    /**
     * 业务标识
     */
    @Eq
    @ApiModelProperty("业务标识")
    private Integer serviceId;
    /**
     * 关联标准频道Id
     */
    @Eq
    @ApiModelProperty("关联标准频道Code")
    private String logicChannelCode;

    /**
     * 频道id，用逗号隔开
     */
    @In(alias = "channelId")
    @ApiModelProperty("频道Id")
    private List<String> channelIdList;

    /**
     * 频道id
     */
    @In(alias = "t.channelId")
    @ApiModelProperty("频道Id")
    private String channelId;

    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @Eq(alias = "t.isDelete")
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;
}