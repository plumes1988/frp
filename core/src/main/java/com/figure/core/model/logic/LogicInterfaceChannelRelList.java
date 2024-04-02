package com.figure.core.model.logic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 切换器接口和节目信息关联
 * </p>
 *
 * @author feather
 * @date 2023-03-06 16:34:01
 */
@Data
@Accessors(chain = true)
@TableName("logic_interface_channel_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicInterfaceChannelRel", description = "切换器接口和节目信息关联")
public class LogicInterfaceChannelRelList extends BaseModel {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("自增主键")
    private Integer id;
    /**
     * 切换器接口Id
     */
    @ApiModelProperty("切换器接口Id")
    private String interfaceId;
    /**
     * 逻辑分析组Id
     */
    @ApiModelProperty("逻辑分析组Id")
    private String groupId;
    /**
     * 信源Id
     */
    @ApiModelProperty("信源Id")
    private String channelId;
    /**
     * 权重
     */
    @ApiModelProperty("权重")
    private Integer channelGrade;
    /**
     * 是否关联切换器 0不关联 1关联
     */
    @ApiModelProperty("是否关联切换器 0不关联 1关联")
    private Integer isLinkSwitch;
    /**
     * 关联切换器编号
     */
    @ApiModelProperty("关联切换器编号")
    private String linkSwitchCode;
    /**
     * 关联切换器的接口序号
     */
    @ApiModelProperty("关联切换器的接口序号")
    private Integer linkSwitchSerialNo;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;


    private String logicChannelName;

    private String groupName;

    private String positionName;

    private String channelName;

    private String linkSwitchName;
}