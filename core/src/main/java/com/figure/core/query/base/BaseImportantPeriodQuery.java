package com.figure.core.query.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.model.base.BaseImportantPeriod;
import com.figure.core.util.copycat.annotaion.Ge;
import com.figure.core.util.copycat.annotaion.Le;
import com.figure.core.util.copycat.annotaion.Like;
import com.figure.core.util.copycat.query.AbstractQuery;
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
 * @author feather
 * @date 2021-04-20 14:03:33
 */
@Data
@Accessors(chain = true)
@TableName("base_important_period")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BaseImportantPeriod重要保障期", description = "重要保障期")
public class BaseImportantPeriodQuery extends AbstractQuery<BaseImportantPeriod> {

    /**
     * 重要保障期名称
     */
    @Like
    @ApiModelProperty("重要保障期名称")
    private String importantPeriodName;
    /**
     * 开始日期
     */
    @Ge
    @ApiModelProperty("开始日期")
    private LocalDate startDate;
    /**
     * 结束日期
     */
    @Le
    @ApiModelProperty("结束日期")
    private LocalDate endDate;
}