package com.figure.core.query.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.model.base.BaseImportantSession;
import com.figure.core.util.copycat.annotaion.Ge;
import com.figure.core.util.copycat.annotaion.Le;
import com.figure.core.util.copycat.annotaion.Like;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalTime;

/**
 * <p>
 * 重点时段
 * </p>
 *
 * @author feather
 * @date 2021-04-20 14:03:33
 */
@Data
@Accessors(chain = true)
@TableName("base_important_session")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BaseImportantSession重点时段", description = "重点时段")
public class BaseImportantSessionQuery extends AbstractQuery<BaseImportantSession> {

    /**
     * 重点时段名称
     */
    @Like
    @ApiModelProperty("重点时段名称")
    private String importantSessionName;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 开始时间
     */
    @Ge
    @ApiModelProperty("开始时间")
    private LocalTime startTime;
    /**
     * 结束时间
     */
    @Le
    @ApiModelProperty("结束时间")
    private LocalTime endTime;

}