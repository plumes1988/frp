package com.figure.core.query.front;

import com.baomidou.mybatisplus.annotation.TableField;
import com.figure.core.model.front.FrontLogicPosition;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Like;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 采集点管理
 * </p>
 *
 * @author feather
 * @date 2021-05-19 17:16:25
 */
@Data
public class FrontLogicPositionQuery extends AbstractQuery<FrontLogicPosition> {

    /**
     * 采集点ID
     */
    @Eq
    @ApiModelProperty("采集点ID")
    private Integer positionId;
    /**
     * 采集点名称
     */
    @Like
    @ApiModelProperty("采集点名称")
    private String positionName;
    /**
     * 采集点名称
     */
    @Like
    @ApiModelProperty("采集点别名")
    private String positionAlias;
    /**
     * 所属监测站ID
     */
    @Eq
    @ApiModelProperty("所属监测站ID")
    private Integer frontId;
    /**
     * 位置信息
     */
    @Like
    @ApiModelProperty("位置信息")
    private String location;
    /**
     * 链路属性
     */
    @Eq
    @ApiModelProperty("链路属性")
    private String linkType;
    /**
     * 链路级别
     */
    @Eq
    @ApiModelProperty("链路级别")
    private String linkLevel;
    /**
     * 同步状态 0：未同步 1：已同步
     */
    @Eq
    @ApiModelProperty("同步状态 0：未同步 1：已同步 ")
    private Integer syncStatus;
    /**
     * 0：停用、1：启用
     */
    @Eq
    @ApiModelProperty("0：停用、1：启用")
    private Integer isEnable;
    /**
     * 0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑
     */
    @Eq
    @ApiModelProperty("0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;
    @Eq
    @TableField(exist = false)
    private Integer regionLong;
    @Eq
    @TableField(exist = false)
    private Integer regionLat;

}