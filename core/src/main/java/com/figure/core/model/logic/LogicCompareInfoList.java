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
 * 异态比对分组配置
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_compare_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicCompareInfo", description = "异态比对分组配置")
public class LogicCompareInfoList extends BaseModel {

    /**
     * 主键id自增
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键id自增")
    private Integer id;
    /**
     * 异态频道分组Id
     */
    @ApiModelProperty("异态频道分组Id")
    private Integer groupId;
    /**
     * 主频道id
     */
    @ApiModelProperty("主频道id")
    private String mainChannelId;
    /**
     * 对比频道Id
     */
    @ApiModelProperty("对比频道Id")
    private String compareChannelId;
    /**
     * 是否关联切换器
     */
    @ApiModelProperty("是否关联切换器")
    private Integer relSwitcher;
    /**
     * 关联切换器Id
     */
    @ApiModelProperty("关联切换器Id")
    private String relSwitcherNumber;
    /**
     * 关联接口Id
     */
    @ApiModelProperty("关联接口Id")
    private String relInterfaceId;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    private Integer mainLogicPositionId;

    private Integer compareLogicPositionId;

    private String mainChannelName;

    private String compareChannelName;

    private String logicChannelCode;

    private String mainLogicPositionName;

    private String compareLogicPositionName;

    private String relInterfaceName;

    private String deviceName;
}