package com.figure.core.model.report;

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
 * 上报设备与指标关联
 * </p>
 *
 *@author feather
 *@date 2024-03-29 14:12:20
 */
@Data
@Accessors(chain = true)
@TableName("report_device_indicator_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ReportDeviceIndicatorRel", description = "上报设备与指标关联")
public class ReportDeviceIndicatorRel extends BaseModel {

    /** 
     *主键 
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;
    /** 
     *设备编号 
     */
    @ApiModelProperty("设备编号")
    private String deviceCode;
    /** 
     *指标编号 
     */
    @ApiModelProperty("指标编号")
    private String indicatorCode;
    /** 
     *指标排序 
     */
    @ApiModelProperty("指标排序")
    private Integer indicatorIdx;
    /** 
     *指标排序 
     */
    @ApiModelProperty("指标排序")
    private Integer isDelete;

}