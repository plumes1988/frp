package com.figure.core.model.spectrum;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 频谱服务和频谱仪设备关联
 * </p>
 *
 *@author feather
 *@date 2024-01-24 14:26:20
 */
@Data
@Accessors(chain = true)
@TableName("spectrum_service_device_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SpectrumServiceDeviceRel", description = "频谱服务和频谱仪设备关联")
public class SpectrumServiceDeviceRel extends BaseModel {

    /**
     *主键 
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;
    /**
     *频谱服务编号 
     */
    @ApiModelProperty("频谱服务编号")
    private String serviceCode;
    /**
     *频谱仪设备编号 
     */
    @ApiModelProperty("频谱仪设备编号")
    private String spectrumCode;
    /**
     * 中心频率单位Hz
     */
    @ApiModelProperty("中心频率单位Hz")
    private Integer centerFrequency;
    /**
     * 带宽单位Hz
     */
    @ApiModelProperty("带宽单位Hz")
    private Integer frequencySpan;
    /**
     * RBW单位Hz
     */
    @ApiModelProperty("RBW单位Hz")
    private Integer RBW;
    /**
     * VBW单位Hz
     */
    @ApiModelProperty("VBW单位Hz")
    private Integer VBW;
    /**
     * 频谱异常阈值dB
     */
    @ApiModelProperty("频谱异常阈值dB")
    private Integer level;
    /**
     * 开始频率单位Hz
     */
    @ApiModelProperty("开始频率单位Hz")
    private Integer startFrequency;
    /**
     * 结束频率单位Hz
     */
    @ApiModelProperty("结束频率单位Hz")
    private Integer endFrequency;

    /**
     * 启用状态 0停用 1启用
     */
    @ApiModelProperty("启用状态 0停用 1启用")
    private Integer spectrumStatus;
    /**
     *状态：0:未删除、1:已删除、2:停用 
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

    /**
     *频谱服务名称
     */
    @TableField(exist = false)
    @ApiModelProperty("频谱服务名称")
    private String serviceName;
    /**
     *频谱仪设备名称
     */
    @TableField(exist = false)
    @ApiModelProperty("频谱仪设备名称")
    private String spectrumName;

}