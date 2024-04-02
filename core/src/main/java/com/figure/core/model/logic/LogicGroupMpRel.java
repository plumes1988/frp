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
 * 逻辑分析组与频道关联
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_group_mp_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicGroupMpRel", description = "逻辑分析组与频道关联")
public class LogicGroupMpRel extends BaseModel {

    /**
     * 逻辑分组模板和采集点关联表
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("逻辑分组模板和采集点关联表")
    private Integer id;
    /**
     * 逻辑分析组模板关联Id
     */
    @ApiModelProperty("逻辑分析组模板关联Id")
    private Integer analysisModelId;
    /**
     * 采集点id
     */
    @ApiModelProperty("采集点id")
    private Integer logicPositionId;
    /**
     * 传输链路级别1-255
     */
    @ApiModelProperty("传输链路级别1-255")
    private Integer chainGrade;
    /**
     * 默认为0， 0：否  ； 1：是 。不做异态分析，只做比对参考辅助分析
     */
    @ApiModelProperty("默认为0， 0：否  ； 1：是 。不做异态分析，只做比对参考辅助分析")
    private Integer auxiliary;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}