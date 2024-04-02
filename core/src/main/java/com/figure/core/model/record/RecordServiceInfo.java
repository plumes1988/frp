package com.figure.core.model.record;

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
 * 收录服务
 * </p>
 *
 * @author feather
 * @date 2023-04-11 11:21:04
 */
@Data
@Accessors(chain = true)
@TableName("record_service_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RecordServiceInfo", description = "收录服务")
public class RecordServiceInfo extends BaseModel {

    /**
     * 收录服务ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("收录服务ID")
    private Integer recordServiceId;
    /**
     * 收录服务名称
     */
    @ApiModelProperty("收录服务名称")
    private String recordServiceName;
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
     * 收录服务管理IP（从设备获取）
     */
    @ApiModelProperty("收录服务管理IP（从设备获取）")
    private String recordServiceIP;
    /**
     * 节点总资源
     */
    @ApiModelProperty("节点总资源")
    private Integer totalBitRate;
    /**
     * 已使用资源
     */
    @ApiModelProperty("已使用资源")
    private Integer usedBitRate;
    /**
     * hls文件时长
     */
    @ApiModelProperty("hls文件时长")
    private Integer hlsFileTime;
    /**
     * 文件时长
     */
    @ApiModelProperty("文件时长")
    private Integer fileTime;
    /**
     * 收录文件存储相对路径，默认/recordfile
     */
    @ApiModelProperty("收录文件存储相对路径，默认/recordfile")
    private String recordPath;
    /**
     * url上下文，默认 "http://节点IP:9800/recordfile"
     */
    @ApiModelProperty("url上下文，默认 http://节点IP:9800/recordfile")
    private String urlContext;
    /**
     * 磁盘空间配额，默认20
     */
    @ApiModelProperty("磁盘空间配额，默认20")
    private Integer diskSpaceQuota;
    /**
     * 运行状态 0待执行 1正常执行 2自动停止 3手动停止 4执行故障 5信源中断
     */
    @ApiModelProperty("运行状态 0待执行 1正常执行 2自动停止 3手动停止 4执行故障 5信源中断")
    private Integer taskStatus = 1;
    /**
     * 故障信息
     */
    @ApiModelProperty("故障信息")
    private String errorInfo;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

    @TableField(exist = false)
    private Integer recordClusterId;
}