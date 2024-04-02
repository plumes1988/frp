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
 * 自定义业务逻辑
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_customize_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicCustomizeInfo", description = "自定义业务逻辑")
public class LogicCustomizeInfo extends BaseModel {

    /**
     * 自定义业务逻辑自增Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("自定义业务逻辑自增Id")
    private Integer id;
    /**
     * 自定义业务逻辑名称
     */
    @ApiModelProperty("自定义业务逻辑名称")
    private String customizeName;
    /**
     * 所属逻辑分组Id
     */
    @ApiModelProperty("所属逻辑分组Id")
    private Integer groupId;
    /**
     * 业务逻辑排序优先级
     */
    @ApiModelProperty("业务逻辑排序优先级")
    private Integer priority;
    /**
     * 自定义业务逻辑描述
     */
    @ApiModelProperty("自定义业务逻辑描述")
    private String description;
    /**
     * 链路结构
     */
    @ApiModelProperty("链路结构 1四边形 2退化单源 3强制单源")
    private Integer lineMode;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}