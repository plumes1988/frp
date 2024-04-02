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
@TableName("logic_group_channel_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicGroupChannelRel", description = "逻辑分析组与频道关联")
public class LogicGroupChannelRel extends BaseModel {

    /**
     * 逻辑分组频道关联表
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("逻辑分组频道关联表")
    private Integer id;
    /**
     * 逻辑分析组关联Id
     */
    @ApiModelProperty("逻辑分析组关联Id")
    private Integer analysisGroupId;
    /**
     * 频道Id
     */
    @ApiModelProperty("频道Id")
    private String channelId;
    /**
     * 频道名称
     */
    @ApiModelProperty("频道名称")
    private String channelName;
    /**
     * 链路属性
     */
    @ApiModelProperty("链路属性")
    private Integer linkType;
    /**
     * 链路级别
     */
    @ApiModelProperty("链路级别")
    private Integer linkLevel;
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