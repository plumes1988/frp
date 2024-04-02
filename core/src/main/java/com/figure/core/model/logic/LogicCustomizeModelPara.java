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
 * 自定义业务逻辑参数模板
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_customize_model_para")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicCustomizeModelPara", description = "自定义业务逻辑参数模板")
public class LogicCustomizeModelPara extends BaseModel {

    /**
     * 参数自增Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("参数自增Id")
    private Integer id;
    /**
     * 关联自定义业务逻辑Id
     */
    @ApiModelProperty("关联自定义业务逻辑模板Id")
    private Integer customizeModelId;
    /**
     * 逻辑 ,1:条件 ；2:结论
     */
    @ApiModelProperty("逻辑 ,1:条件 ；2:结论")
    private Integer logic;
    /**
     * 来源 1:信源; 2:比对 3:消息
     */
    @ApiModelProperty("来源 1:信源; 2:比对 3:消息")
    private Integer source;
    /**
     * 采集点1Id
     */
    @ApiModelProperty("采集点1Id")
    private Integer positionId1;
    /**
     * 采集点2Id
     */
    @ApiModelProperty("采集点2Id")
    private Integer positionId2;
    /**
     * 消息
     */
    @ApiModelProperty("消息")
    private String message;
    /**
     * 链路结构 逗号隔开
     */
    @ApiModelProperty("链路结构 逗号隔开")
    private String linkStruct;
    /**
     * 条件状态 0:正常 1:异常
     */
    @ApiModelProperty("条件状态 0:正常 1:异常")
    private Integer anomalStatus;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}