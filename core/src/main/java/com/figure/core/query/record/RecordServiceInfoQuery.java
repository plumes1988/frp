package com.figure.core.query.record;

import com.figure.core.model.record.RecordServiceInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.In;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RecordServiceInfoQuery extends AbstractQuery<RecordServiceInfo> {

    @Eq
    @ApiModelProperty("收录服务ID")
    private Integer recordServiceId;

    @In(alias = "recordServiceId")
    @ApiModelProperty("收录服务ID")
    private List<String> recordServiceIdList;

    @In(alias = "serviceCode")
    @ApiModelProperty("收录服务ID")
    private List<String> serviceCodeList;

    @Eq
    @ApiModelProperty("收录服务名称")
    private String recordServiceName;

    @Eq
    @ApiModelProperty("前端ID")
    private Integer frontId;

    @Eq
    @ApiModelProperty("设备编号")
    private String serviceCode;

    @Eq
    @ApiModelProperty("设备名称")
    private String serviceName;

    @Eq
    @ApiModelProperty("收录服务管理IP（从设备获取）")
    private String recordServiceIP;

    @Eq
    @ApiModelProperty("节点总资源")
    private Integer totalBitRate;

    @Eq
    @ApiModelProperty("已使用资源")
    private Integer usedBitRate;

    @Eq
    @ApiModelProperty("hls文件时长")
    private Integer hlsFileTime;

    @Eq
    @ApiModelProperty("文件时长")
    private Integer fileTime;

    @Eq
    @ApiModelProperty("收录文件存储相对路径，默认/recordfile")
    private String recordPath;

    @Eq
    @ApiModelProperty("url上下文，默认 http://节点IP:9800/recordfile")
    private String urlContext;

    @Eq
    @ApiModelProperty("磁盘空间配额，默认20")
    private Integer diskSpaceQuota;

    @Eq
    @ApiModelProperty("运行状态 0待执行 1正常执行 2自动停止 3手动停止 4执行故障 5信源中断")
    private Integer taskStatus;

    @Eq
    @ApiModelProperty("故障信息")
    private String errorInfo;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;
}