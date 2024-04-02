package com.figure.core.model.logic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.figure.core.base.BaseModel;


/**
 * <p>
 * 逻辑分析组模板表
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_group_model")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicGroupModel", description = "逻辑分析组模板表")
public class LogicGroupModel extends BaseModel {

    /**
     * 逻辑分析组模板主键Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("逻辑分析组模板主键Id")
    private Integer id;
    /**
     * 逻辑分析组模板名称
     */
    @ApiModelProperty("逻辑分析组模板名称")
    private String modelName;
    /**
     * 采集站Id
     */
    @ApiModelProperty("采集站Id")
    private Integer monitorId;
    /**
     * 标准频道主键Id
     */
    @ApiModelProperty("标准频道主键Id")
    private Integer standardId;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}