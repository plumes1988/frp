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
 * 报警门限规则信息
 * </p>
 *
 * @author feather
 * @date 2023-03-07 16:00:32
 */
@Data
@Accessors(chain = true)
@TableName("logic_threshold_rule")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicThresholdRule", description = "报警门限规则信息")
public class LogicThresholdRule extends BaseModel {

    /**
     * 报警规则模板Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("报警规则模板Id")
    private Integer modelId;
    /**
     * 报警规则名称
     */
    @ApiModelProperty("报警规则名称")
    private String modelName;
    /**
     * 规则描述
     */
    @ApiModelProperty("规则描述")
    private String description;
    /**
     * 触发关键词 逗号隔开 栏目名或节目名包含
     */
    @ApiModelProperty("触发关键词 逗号隔开 栏目名或节目名包含")
    private String keywords;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}