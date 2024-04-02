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
 * 单位信息
 * </p>
 *
 *@author feather
 *@date 2021-04-20 14:03:33
 */
@Data
@Accessors(chain = true)
@TableName("base_department_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BaseDepartmentInfo单位信息", description = "单位信息")
public class BaseDepartmentInfo extends BaseModel {

    /** 单位ID */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("单位ID")
    private Long departmentId;
    /** 单位名称 */
    @ApiModelProperty("单位名称")
    private String departmentName;
    /** 单位属性 */
    @ApiModelProperty("单位属性")
    private Long departmentType;

}