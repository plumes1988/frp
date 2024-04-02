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
 * 节目和报警门限规则关联
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_threshold_rule_program_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicThresholdRuleProgramRel", description = "节目和报警门限规则关联")
public class LogicThresholdRuleProgramRel extends BaseModel {

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("")
    private Integer id;
    /**
     * 节目id
     */
    @ApiModelProperty("节目id")
    private String programId;
    /**
     * 门限模板id
     */
    @ApiModelProperty("门限模板id")
    private Integer modelId;

}