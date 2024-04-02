package com.figure.core.query.signal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.model.signal.SignalFrequencyInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Like;
import com.figure.core.util.copycat.query.AbstractQuery;
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
public class SignalFrequencyInfoQuery extends AbstractQuery<SignalFrequencyInfo> {

    /**
     * 频率名称
     */
    @Like
    @ApiModelProperty("频率名称")
    private String frequencyName;
    /**
     * 监测站ID
     */
    @Eq
    @ApiModelProperty("监测站ID")
    private Integer frontId;
    /**
     * 监测点ID
     */
    @Eq
    @ApiModelProperty("监测点ID")
    private Integer logicPositionId;
    /**
     * 码率
     */
    @Eq
    @ApiModelProperty("码率")
    private Integer bitRate;
    /**
     * 信号编码
     */
    @Eq
    @ApiModelProperty("信号编码")
    private Integer signalCode;
    /**
     * 频率值
     */
    @Eq
    @ApiModelProperty("频率值")
    private String frequencyValue;
    /**
     * 接口ID
     */
    @Eq
    @ApiModelProperty("接口ID")
    private Integer interfaceId;
    /**
     * 同步标识 0：合法 1：非法   (给前端使用)
     */
    @Eq
    @ApiModelProperty("同步标识 0：合法 1：非法   (给前端使用)")
    private Integer frequencyFlag;
    /**
     * 同步状态 0：已同步 1：未同步 2:新频 3:待删除4:已删除
     */
    @Eq
    @ApiModelProperty("同步状态 0：已同步 1：未同步 2:新频 3:待删除4:已删除")
    private Integer syncStatus;

    /**
     * 状态：0:删除、1:正常、2:停用
     */
    @Eq
    @ApiModelProperty("状态：0:删除、1:正常、2:停用")
    private Integer isDelete;

}