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
 * 录制资源和录制集群关联
 * </p>
 *
 * @author feather
 * @date 2023-04-10 10:23:52
 */
@Data
@Accessors(chain = true)
@TableName("record_service_cluster_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RecordServiceClusterRel", description = "录制资源和录制集群关联")
public class RecordServiceClusterRel extends BaseModel {

    /**
     * 主键自增Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键自增Id")
    private Integer id;
    /**
     * 录制集群Id
     */
    @ApiModelProperty("录制集群Id")
    private Integer recordClusterId;
    /**
     * 录制资源Id
     */
    @ApiModelProperty("录制资源Id")
    private Integer recordServiceId;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}