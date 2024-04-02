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

import java.time.LocalDate;

/**
 * <p>
 * 重要保障期
 * </p>
 *
 *@author feather
 *@date 2021-04-20 14:03:33
 */
@Data
@Accessors(chain = true)
@TableName("base_important_period")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BaseImportantPeriod重要保障期", description = "重要保障期")
public class BaseImportantPeriod extends BaseModel {

    /**
     * 重要保障期ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("重要保障期ID")
    private Long importantPeriodId;
    /**
     * 重要保障期名称
     */
    @ApiModelProperty("重要保障期名称")
    private String importantPeriodName;
    /**
     * 开始日期
     */
    @ApiModelProperty("开始日期")
    private LocalDate startDate;
    /**
     * 结束日期
     */
    @ApiModelProperty("结束日期")
    private LocalDate endDate;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}