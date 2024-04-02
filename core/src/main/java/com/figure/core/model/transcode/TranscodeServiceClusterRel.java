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
 * 转码资源和转码集群关联
 * </p>
 *
 * @author feather
 * @date 2023-04-11 09:45:29
 */
@Data
@Accessors(chain = true)
@TableName("transcode_service_cluster_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TranscodeServiceClusterRel", description = "转码资源和转码集群关联")
public class TranscodeServiceClusterRel extends BaseModel {

    /**
     * 主键自增Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键自增Id")
    private Integer id;
    /**
     * 转码集群Id
     */
    @ApiModelProperty("转码集群Id")
    private Integer transcodeClusterId;
    /**
     * 转码资源Id
     */
    @ApiModelProperty("转码资源Id")
    private Integer transcodeServiceId;
    /**
     * 状态：0:删除、1:正常、2:停用
     */
    @ApiModelProperty("状态：0:删除、1:正常、2:停用")
    private Integer isDelete;

}