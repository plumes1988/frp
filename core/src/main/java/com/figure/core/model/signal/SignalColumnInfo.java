package com.figure.core.model.signal;

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
 * 栏目信息
 * </p>
 *
 * @author feather
 * @date 2022-08-16 11:23:19
 */
@Data
@Accessors(chain = true)
@TableName("signal_column_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalColumnInfo", description = "栏目信息")
public class SignalColumnInfo extends BaseModel {

    /**
     * 栏目ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("栏目ID")
    private Integer id;
    /**
     * 逻辑频道ID
     */
    @ApiModelProperty("逻辑频道ID")
    private String logicChannelId;
    /**
     * 栏目名称
     */
    @ApiModelProperty("栏目名称")
    private String columnName;
    /**
     * 0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑
     */
    @ApiModelProperty("0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;

}