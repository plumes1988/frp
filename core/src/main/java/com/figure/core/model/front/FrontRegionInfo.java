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

import java.math.BigDecimal;

/**
 * <p>
 * 行政区域信息
 * </p>
 *
 *@author feather
 *@date 2021-05-19 17:16:25
 */
@Data
@Accessors(chain = true)
@TableName("front_region_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FrontRegionInfo行政区域信息", description = "行政区域信息")
public class FrontRegionInfo{

    /** 区域Id */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("区域Id")
    private Integer regionId;
    /** 区域名称 */
    @ApiModelProperty("区域名称")
    private String regionName;
    /** 行政级别：0：全国 1：省 2：市  3：区/县，4：居委/乡镇，5：街道/村，99:无行政级别归属
行政级别：0：全国 1：省 2：市  3：区/县，4：居委/乡镇，5：街道/村，99:无行政级别归属 */
    @ApiModelProperty("行政级别：0：全国 1：省 2：市  3：区/县，4：居委/乡镇，5：街道/村，99:无行政级别归属")
    private Integer regoinalLevel;
    /** 区域行政码，从所属行政级别，关联区域信息id中获取 */
    @ApiModelProperty("区域行政码，从所属行政级别，关联区域信息id中获取")
    private Integer regionCode;
    /** 上级行政区域ID */
    @ApiModelProperty("上级行政区域ID")
    private Long supRegionId;
    /** 关联区/县信息ID */
    @ApiModelProperty("关联区/县信息ID")
    private Integer districtsId;
    /** 关联市信息ID */
    @ApiModelProperty("关联市信息ID")
    private Integer cityId;
    /** 关联省信息ID */
    @ApiModelProperty("关联省信息ID")
    private Integer provincesId;
    /** 纬度 */
    @ApiModelProperty("纬度")
    private String regionLat;
    /** 经度 */
    @ApiModelProperty("经度")
    private String regionLong;
    /** 西经 */
    @ApiModelProperty("西经")
    private BigDecimal leftLongitude;
    /** 北纬 */
    @ApiModelProperty("北纬")
    private BigDecimal topLatitude;
    /** 东经 */
    @ApiModelProperty("东经")
    private BigDecimal rightLongitude;
    /** 南纬 */
    @ApiModelProperty("南纬")
    private BigDecimal bottomLatitude;
    /** 文件名称 */
    @ApiModelProperty("文件名称")
    private String fileName;
    /** Map影片剪辑绑定Id */
    @ApiModelProperty("Map影片剪辑绑定Id")
    private String flagOfMap;

}