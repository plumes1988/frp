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
 * 机构信息
 * </p>
 *
 *@author feather
 *@date 2021-04-20 14:03:32
 */
@Data
@Accessors(chain = true)
@TableName("base_agency_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BaseAgencyInfo机构信息", description = "机构信息")
public class BaseAgencyInfo extends BaseModel {

    /** 机构ID */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("机构ID")
    private Long agencyId;
    /** 机构名称 */
    @ApiModelProperty("机构名称")
    private String agencyName;

}