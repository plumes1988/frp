package com.figure.core.model.spectrum;

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
 * 频谱仪参数设置模板
 * </p>
 *
 *@author feather
 *@date 2024-03-29 14:12:20
 */
@Data
@Accessors(chain = true)
@TableName("spectrum_param_template")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SpectrumParamTemplate", description = "频谱仪参数设置模板")
public class SpectrumParamTemplate extends BaseModel {

    /** 
     *主键 
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;
    /**
     *模板名称
     */
    @ApiModelProperty("模板名称")
    private String templateName;
    /** 
     *中心频率单位Hz 
     */
    @ApiModelProperty("中心频率单位Hz")
    private Integer centerFrequency;
    /**
     *带宽单位Hz 
     */
    @ApiModelProperty("带宽单位Hz")
    private Integer frequencySpan;
    /** 
     *RBW单位Hz 
     */
    @ApiModelProperty("RBW单位Hz")
    private Integer RBW;
    /** 
     *VBW单位Hz 
     */
    @ApiModelProperty("VBW单位Hz")
    private Integer VBW;
    /** 
     *频谱异常阈值dB 
     */
    @ApiModelProperty("频谱异常阈值dB")
    private Integer level;
    /** 
     *开始频率单位Hz 
     */
    @ApiModelProperty("开始频率单位Hz")
    private Integer startFrequency;
    /** 
     *结束频率单位Hz 
     */
    @ApiModelProperty("结束频率单位Hz")
    private Integer endFrequency;
    /** 
     *状态：0:未删除、1:已删除、2:停用 
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}