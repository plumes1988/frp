package com.figure.core.model.signal;

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
 * 频率信息
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:38
 */
@Data
@Accessors(chain = true)
@TableName("signal_frequency_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalFrequencyInfo频率信息", description = "频率信息")
public class SignalFrequencyInfoList extends BaseModel {

    /**
     * 频率ID
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty("频率ID")
    private String frequencyId;
    /**
     * 频率名称
     */
    @ApiModelProperty("频率名称")
    private String frequencyName;
    /**
     * 监测站ID
     */
    @ApiModelProperty("监测站ID")
    private Integer frontId;
    /**
     * 监测点ID
     */
    @ApiModelProperty("监测点ID")
    private Integer logicPositionId;
    /**
     * 信号编码
     */
    @ApiModelProperty("信号编码")
    private Integer signalCode;
    /**
     * 频率值
     */
    @ApiModelProperty("频率值")
    private String frequencyValue;
    /**
     * 接口ID
     */
    @ApiModelProperty("接口ID")
    private Integer interfaceId;
    /**
     * 同步标识 0：合法 1：非法   (给前端使用)
     */
    @ApiModelProperty("同步标识 0：合法 1：非法   (给前端使用)")
    private Integer frequencyFlag;
    /**
     * 同步状态 0：已同步 1：未同步 2:新频 3:待删除4:已删除
     */
    @ApiModelProperty("同步状态 0：已同步 1：未同步 2:新频 3:待删除4:已删除")
    private Integer syncStatus;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    private Integer signalUnit;

}