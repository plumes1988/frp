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


/**
 * <p>
 * 转码集群管理
 * </p>
 *
 * @author feather
 * @date 2023-04-11 09:45:29
 */
@Data
@Accessors(chain = true)
@TableName("transcode_cluster_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TranscodeClusterInfo", description = "转码集群管理")
public class TranscodeClusterInfo extends BaseModel {

    /**
     * 转码集群Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("转码集群Id")
    private Integer transcodeClusterId;
    /**
     * 转码集群名称
     */
    @ApiModelProperty("转码集群名称")
    private String transcodeClusterName;
    /**
     * 集群总资源
     */
    @ApiModelProperty("集群总资源")
    private Integer totalResources;
    /**
     * 已使用资源
     */
    @ApiModelProperty("已使用资源")
    private Integer usedResources;
    /**
     * 所属前端Id
     */
    @ApiModelProperty("所属前端Id")
    private Integer frontId;
    /**
     * 状态：0:删除、1:正常、2:停用
     */
    @ApiModelProperty("状态：0:删除、1:正常、2:停用")
    private Integer isDelete;

}