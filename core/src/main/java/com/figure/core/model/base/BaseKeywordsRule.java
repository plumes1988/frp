package com.figure.core.model.base;

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
 * 关键词规则
 * </p>
 *
 *@author feather
 *@date 2021-04-20 14:03:33
 */
@Data
@Accessors(chain = true)
@TableName("base_keywords_rule")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BaseKeywordsRule关键词规则", description = "关键词规则")
public class BaseKeywordsRule extends BaseModel {

    /** 主键 */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long ruleId;
    /** 规则名称 */
    @ApiModelProperty("规则名称")
    private String ruleName;
    /** 必须关键字，逗号分隔 */
    @ApiModelProperty("必须关键字，逗号分隔")
    private String mandatoryKeywords;
    /** 可选关键字，逗号分隔 */
    @ApiModelProperty("可选关键字，逗号分隔")
    private String requiredKeywords;
    /** 过滤关键词，逗号分隔 */
    @ApiModelProperty("过滤关键词，逗号分隔")
    private String ignoreKeywords;
    /** 0：停用，1：启用 */
    @ApiModelProperty("0：停用，1：启用")
    private Integer isEnable;
    /** 0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑 */
    @ApiModelProperty("0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;
    /** 是否已同步至各前端 0：未同步 1：已同步 */
    @ApiModelProperty("是否已同步至各前端 0：未同步 1：已同步")
    private Integer synStatus;

}