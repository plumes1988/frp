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

import java.util.List;


/**
 * <p>
 * 逻辑分析组与设备关联
 * </p>
 *
 * @author feather
 * @date 2023-03-10 16:19:09
 */
@Data
@Accessors(chain = true)
@TableName("logic_group_device_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicGroupDeviceRel", description = "逻辑分析组与设备关联")
public class LogicGroupDeviceRelList extends BaseModel {

    /**
     * 自增Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("自增Id")
    private Integer id;
    /**
     * 播出系统编号
     */
    @ApiModelProperty("播出系统编号")
    private String channelCode;
    /**
     * 关联网管设备编号
     */
    @ApiModelProperty("关联网管设备编号")
    private String deviceCode;
    /**
     * 逻辑分析组Id
     */
    @ApiModelProperty("逻辑分析组Id")
    private Integer groupId;
    /**
     * 设备与信号关联模式 1自动，根据设备关联采集点链路属性自动关联   2手动  用户手动设置
     */
    @ApiModelProperty("设备与信号关联模式 1自动，根据设备关联采集点链路属性自动关联   2手动  用户手动设置")
    private Integer linkMode;
    /**
     * 关联信号id，逗号隔开，从逻辑分析组配置的信源中选择
     */
    @ApiModelProperty("关联信号id，逗号隔开，从逻辑分析组配置的信源中选择")
    private String channelIds;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    private String linkType;

    private String linkTypeName;

    private String deviceType;

    private String deviceTypeName;

    private String deviceSubType;

    private String deviceSubTypeName;

    private String positionName;

    private String logicPositionId;

    private String deviceName;

    private List<ChannelIdInfo> channelIdInfoList;

    @Data
    public static class ChannelIdInfo {

        private String channelId;

        private String channelName;

        private String positionName;

    }
}