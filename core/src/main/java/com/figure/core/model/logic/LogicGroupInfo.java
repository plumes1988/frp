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
 * 逻辑分析组
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_group_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicGroupInfo", description = "逻辑分析组")
public class LogicGroupInfo extends BaseModel {

    /**
     * 逻辑分析组主键Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("逻辑分析组主键Id")
    private Integer id;
    /**
     * 逻辑分析组名称
     */
    @ApiModelProperty("逻辑分析组名称")
    private String groupName;
    /**
     * 前端Id
     */
    @ApiModelProperty("前端Id")
    private Integer frontId;
    /**
     * 业务类型：1综合逻辑分析 2播出预监测
     */
    @ApiModelProperty("业务类型：1综合逻辑分析 2播出预监测")
    private Integer serviceType;
    /**
     * 逻辑频道Id
     */
    @ApiModelProperty("逻辑频道Id")
    private String logicChannelCode;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    /**
     * 关联逻辑中心服务设备编号，从设备关联选择
     */
    @ApiModelProperty("关联逻辑中心服务设备编号，从设备关联选择")
    private String deviceCode;

}