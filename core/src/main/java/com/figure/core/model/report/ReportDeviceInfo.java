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
 * 上报服务设备管理
 * </p>
 *
 *@author feather
 *@date 2024-03-29 14:12:20
 */
@Data
@Accessors(chain = true)
@TableName("report_device_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ReportDeviceInfo", description = "上报服务设备管理")
public class ReportDeviceInfo extends BaseModel {

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
     *系统ID,数据字典配置 
     */
    @ApiModelProperty("系统ID,数据字典配置")
    private Integer systemID;
    /** 
     *设备类型关键字,数据字典配置 
     */
    @ApiModelProperty("设备类型关键字,数据字典配置")
    private String typeKeywords;
    /** 
     *主备特性, M:主 B:备 SW:切换器 
     */
    @ApiModelProperty("主备特性, M:主 B:备 SW:切换器")
    private String featureType;
    /** 
     *序号 
     */
    @ApiModelProperty("序号")
    private String deviceIdx;
    /** 
     *关联上报服务编号 
     */
    @ApiModelProperty("关联上报服务编号")
    private String reportServiceCode;
    /** 
     *状态：0:未删除、1:已删除、2:停用 
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}