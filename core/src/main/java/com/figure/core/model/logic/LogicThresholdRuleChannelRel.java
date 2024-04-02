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
 * 信号频道和报警门限规则关联
 * </p>
 *
 * @author feather
 * @date 2023-03-07 16:00:32
 */
@Data
@Accessors(chain = true)
@TableName("logic_threshold_rule_channel_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicThresholdRuleChannelRel", description = "信号频道和报警门限规则关联")
public class LogicThresholdRuleChannelRel extends BaseModel {

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("")
    private Integer id;
    /**
     * 频道id
     */
    @ApiModelProperty("频道id")
    private String channelId;
    /**
     * 门限模板id
     */
    @ApiModelProperty("门限模板id")
    private Integer modelId;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}