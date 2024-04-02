package com.figure.core.model.report;

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
 * 上报服务管理
 * </p>
 *
 *@author feather
 *@date 2024-03-29 14:12:20
 */
@Data
@Accessors(chain = true)
@TableName("report_service_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ReportServiceInfo", description = "上报服务管理")
public class ReportServiceInfo extends BaseModel {

    /** 
     *主键 
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;
    /** 
     *服务名称 
     */
    @ApiModelProperty("服务名称")
    private String serviceName;
    /** 
     *服务编号 
     */
    @ApiModelProperty("服务编号")
    private String serviceCode;
    /** 
     *关联设备ID 
     */
    @ApiModelProperty("关联设备ID")
    private Integer deviceID;
    /**
     *通讯IP 
     */
    @ApiModelProperty("通讯IP")
    private String serviceIP;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

    @TableField(exist = false)
    private String[] serviceCodes;
}