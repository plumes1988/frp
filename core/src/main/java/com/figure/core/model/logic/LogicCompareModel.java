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
 * 异态比对分组配置模板
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_compare_model")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicCompareModel", description = "异态比对分组配置模板")
public class LogicCompareModel extends BaseModel {

    /**
     * 主键id自增
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键id自增")
    private Integer id;
    /**
     * 异态频道分组Id
     */
    @ApiModelProperty("异态频道分组Id")
    private Integer groupModelId;
    /**
     * 主采集点id
     */
    @ApiModelProperty("主采集点id")
    private Integer mainPositionId;
    /**
     * 对比采集点id
     */
    @ApiModelProperty("对比采集点id")
    private Integer comparePositionId;
    /**
     * 是否关联切换器
     */
    @ApiModelProperty("是否关联切换器")
    private Integer relSwitcher;
    /**
     * 关联切换器Id
     */
    @ApiModelProperty("关联切换器Id")
    private String relSwitcherNumber;
    /**
     * 关联接口Id
     */
    @ApiModelProperty("关联接口Id")
    private Integer relInterfaceId;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}