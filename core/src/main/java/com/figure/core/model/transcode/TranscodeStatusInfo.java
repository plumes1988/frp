package com.figure.core.model.transcode;

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
 * 转码服务节点实时状态
 * </p>
 *
 * @author feather
 * @date 2023-04-11 09:45:29
 */
@Data
@Accessors(chain = true)
@TableName("transcode_status_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TranscodeStatusInfo", description = "转码服务节点实时状态")
public class TranscodeStatusInfo extends BaseModel {

    /**
     * 转码服务Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("转码服务Id")
    private Integer transcodeId;
    /**
     * 在线状态
     */
    @ApiModelProperty("在线状态")
    private Integer serverStatus;
    /**
     * 连续运行时长
     */
    @ApiModelProperty("连续运行时长")
    private Integer serverRuntime;
    /**
     * cpu使用率
     */
    @ApiModelProperty("cpu使用率")
    private Integer cpuUsage;
    /**
     * 内存使用率
     */
    @ApiModelProperty("内存使用率")
    private Integer memoryUsage;
    /**
     * GPU使用率
     */
    @ApiModelProperty("GPU使用率")
    private Integer gpuUsage;
    /**
     * 显存使用率
     */
    @ApiModelProperty("显存使用率")
    private Integer gMemoryUsage;
    /**
     * 硬盘使用率
     */
    @ApiModelProperty("硬盘使用率")
    private Integer diskUsage;
    /**
     * 资源使用数
     */
    @ApiModelProperty("资源使用数")
    private Integer usedBitRate;
    /**
     * 资源总数
     */
    @ApiModelProperty("资源总数")
    private Integer totalBitRate;
    /**
     * 最后更新时间点
     */
    @ApiModelProperty("最后更新时间点")
    private Date lastUpdateTime;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}