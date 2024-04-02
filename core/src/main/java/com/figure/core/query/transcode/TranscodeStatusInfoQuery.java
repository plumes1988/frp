package com.figure.core.query.transcode;

import com.figure.core.model.transcode.TranscodeStatusInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TranscodeStatusInfoQuery extends AbstractQuery<TranscodeStatusInfo> {

    @Eq
    @ApiModelProperty("转码服务Id")
    private Integer transcodeId;

    @Eq
    @ApiModelProperty("在线状态")
    private Integer serverStatus;

    @Eq
    @ApiModelProperty("连续运行时长")
    private Integer serverRuntime;

    @Eq
    @ApiModelProperty("cpu使用率")
    private Integer cpuUsage;

    @Eq
    @ApiModelProperty("内存使用率")
    private Integer memoryUsage;

    @Eq
    @ApiModelProperty("GPU使用率")
    private Integer gpuUsage;

    @Eq
    @ApiModelProperty("显存使用率")
    private Integer gMemoryUsage;

    @Eq
    @ApiModelProperty("硬盘使用率")
    private Integer diskUsage;

    @Eq
    @ApiModelProperty("资源使用数")
    private Integer usedBitRate;

    @Eq
    @ApiModelProperty("资源总数")
    private Integer totalBitRate;

    @Eq
    @ApiModelProperty("最后更新时间点")
    private Date lastUpdateTime;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}