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
 * 逻辑中心服务器与逻辑分析组关联
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_server_group_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicServerGroupRel", description = "逻辑中心服务器与逻辑分析组关联")
public class LogicServerGroupRel extends BaseModel {

    /**
     * 自增Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("自增Id")
    private Integer id;
    /**
     * 逻辑中心服务器编号
     */
    @ApiModelProperty("逻辑中心服务器编号")
    private String serverNumber;
    /**
     * 逻辑分析组Id
     */
    @ApiModelProperty("逻辑分析组Id")
    private Integer groupId;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}