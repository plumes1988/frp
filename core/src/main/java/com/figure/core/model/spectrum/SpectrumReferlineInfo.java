package com.figure.core.model.spectrum;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 频谱仪参考频谱
 * </p>
 *
 * @author feather
 * @date 2023-12-07 13:48:22
 */
@Data
@Accessors(chain = true)
@TableName("spectrum_referline_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SpectrumReferlineInfo", description = "频谱仪参考频谱")
public class SpectrumReferlineInfo extends BaseModel {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;
    /**
     * 时段频谱名称
     */
    @ApiModelProperty("时段频谱名称")
    private String referName;
    /**
     * 频谱仪deviceCode
     */
    @ApiModelProperty("频谱仪deviceCode")
    private String spectrumCode;
    /**
     * 电平
     */
    @ApiModelProperty("电平")
    private Integer level;
    /**
     * 频谱数据:存放场强、电平的json结构  {"line":[890,605,483,793,478,364,302,312,345,790]}
     */
    @ApiModelProperty("频谱数据:存放场强、电平的json结构")
    private String spectrumData;
    /**
     * 开始日期
     */
    @ApiModelProperty("开始日期")
    private Date dateStart;
    /**
     * 结束日期
     */
    @ApiModelProperty("结束日期")
    private Date dateEnd;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date timeStart;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date timeEnd;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}