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
 * 重点时段和报警门限规则关联
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_threshold_rule_session_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicThresholdRuleSessionRel", description = "重点时段和报警门限规则关联")
public class LogicThresholdRuleSessionRel extends BaseModel {

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("")
    private Integer id;
    /**
     * 重要保障期Id
     */
    @ApiModelProperty("重要保障期Id")
    private Integer sessionId;
    /**
     * 门限模板id
     */
    @ApiModelProperty("门限模板id")
    private Integer modelId;

}