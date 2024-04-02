package com.figure.core.model.front;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.helper.BytesHelper;
import com.figure.core.util.JSONUtil;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * <p>
 * 采集点管理
 * </p>
 *
 *@author feather
 *@date 2021-05-19 17:16:25
 */
@Data
@Accessors(chain = true)
@TableName("front_logic_position")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FrontLogicPosition采集点管理", description = "采集点管理")
public class FrontLogicPosition extends BaseModel {

    /**
     * 采集点ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("采集点ID")
    private Integer positionId;
    /**
     * 采集点名称
     */
    @ApiModelProperty("采集点名称")
    private String positionName;
    /**
     * 采集点别名
     */
    @ApiModelProperty("采集点别名")
    private String positionAlias;
    /**
     * 所属监测站ID
     */
    @ApiModelProperty("所属监测站ID")
    private Integer frontId;
    /**
     * 位置信息
     */
    @ApiModelProperty("位置信息")
    private String location;
    /**
     * 链路属性
     */
    @ApiModelProperty("链路属性")
    private String linkType;
    /**
     * 链路级别
     */
    @ApiModelProperty("链路级别")
    private String linkLevel;
    /**
     * 同步状态 0：未同步 1：已同步
     */
    @ApiModelProperty("同步状态 0：未同步 1：已同步 ")
    private Integer syncStatus;
    /**
     * 0：停用、1：启用
     */
    @ApiModelProperty("0：停用、1：启用")
    private Integer isEnable;
    /**
     * 0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑
     */
    @ApiModelProperty("0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;

    @TableField(exist = false)
    private Integer regionLong;

    @TableField(exist = false)
    private Integer regionLat;

    public void toBytes(ByteArrayOutputStream out) throws IOException {
        out.write(BytesHelper.getIntBytes(this.positionId));

        out.write(BytesHelper.getIntBytes(this.regionLong));

        out.write(BytesHelper.getIntBytes(this.regionLat));

        System.out.println("前端同步信息：-》"+ JSONUtil.Object2JsonStr(this));
    }
}