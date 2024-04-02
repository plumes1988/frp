package com.figure.core.model.transcode;

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
 * 转码资源管理
 * </p>
 *
 * @author feather
 * @date 2023-04-11 09:45:29
 */
@Data
@Accessors(chain = true)
@TableName("transcode_service_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TranscodeServiceInfo", description = "转码资源管理")
public class TranscodeServiceInfo extends BaseModel {

    /**
     * 转码服务ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("转码服务ID")
    private Integer transcodeServiceId;
    /**
     * 转码服务名称（有别于设备名称）
     */
    @ApiModelProperty("转码服务名称（有别于设备名称）")
    private String transcodeServiceName;
    /**
     * 前端ID
     */
    @ApiModelProperty("前端ID")
    private Integer frontId;
    /**
     * 设备编号
     */
    @ApiModelProperty("设备编号")
    private String serviceCode;
    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    private String serviceName;
    /**
     * 转码服务IP（从设备获取）
     */
    @ApiModelProperty("转码服务IP（从设备获取）")
    private String transcodeServiceIP;
    /**
     * 节点总资源
     */
    @ApiModelProperty("节点总资源")
    private Integer totalResources;
    /**
     * 已使用资源
     */
    @ApiModelProperty("已使用资源")
    private Integer usedResources;
    /**
     * 是否使用GPU：0不用，1使用
     */
    @ApiModelProperty("是否使用GPU：0不用，1使用")
    private Integer useGPU;
    /**
     * 状态：0:删除、1:正常、2:停用
     */
    @ApiModelProperty("状态：0:删除、1:正常、2:停用")
    private Integer isDelete;

    @TableField(exist = false)
    private Integer transcodeClusterId;

    @TableField(exist = false)
    private String frontName;

    @TableField(exist = false)
    private String transcodeClusterName;
}