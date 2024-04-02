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
 * 自定义业务逻辑参数
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_customize_info_para")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicCustomizeInfoPara", description = "自定义业务逻辑参数")
public class LogicCustomizeInfoPara extends BaseModel {

    /**
     * 参数自增Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("参数自增Id")
    private Integer id;
    /**
     * 关联自定义业务逻辑Id
     */
    @ApiModelProperty("关联自定义业务逻辑Id")
    private Integer customizeId;
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
     * 频道1
     */
    @ApiModelProperty("频道1")
    private String channelId1;
    /**
     * 频道2
     */
    @ApiModelProperty("频道2")
    private String channelId2;
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