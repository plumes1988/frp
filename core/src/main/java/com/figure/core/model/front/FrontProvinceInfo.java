package com.figure.core.model.front;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 省份信息
 * </p>
 *
 *@author feather
 *@date 2021-05-19 17:16:25
 */
@Data
@Accessors(chain = true)
@TableName("front_province_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FrontProvinceInfo省份信息", description = "省份信息")
public class FrontProvinceInfo{

    /**  */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("")
    private Integer provinceId;
    /** 区域行政码 */
    @ApiModelProperty("区域行政码")
    private Long regionCode;
    /** 名称 */
    @ApiModelProperty("名称")
    private String provinceName;
    /**  */
    @ApiModelProperty("")
    private String mapName;

}