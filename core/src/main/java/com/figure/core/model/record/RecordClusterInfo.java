package com.figure.core.model.record;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 录制集群管理
 * </p>
 *
 * @author feather
 * @date 2023-04-11 11:21:03
 */
@Data
@Accessors(chain = true)
@TableName("record_cluster_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RecordClusterInfo", description = "录制集群管理")
public class RecordClusterInfo extends BaseModel {

    /**
     * 录制集群Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("录制集群Id")
    private Integer recordClusterId;
    /**
     * 录制集群名称
     */
    @ApiModelProperty("录制集群名称")
    private String recordClusterName;
    /**
     * 集群总资源
     */
    @ApiModelProperty("集群总资源")
    private Integer totalBitRate;
    /**
     * 已使用资源
     */
    @ApiModelProperty("已使用资源")
    private Integer usedBitRate;
    /**
     * 所属前端Id
     */
    @ApiModelProperty("所属前端Id")
    private Integer frontId;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}