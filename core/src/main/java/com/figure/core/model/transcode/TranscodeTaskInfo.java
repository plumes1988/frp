package com.figure.core.model.transcode;

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

import java.util.List;


/**
 * <p>
 * 转码任务管理
 * </p>
 *
 * @author feather
 * @date 2023-04-11 09:45:29
 */
@Data
@Accessors(chain = true)
@TableName("transcode_task_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TranscodeTaskInfo", description = "转码任务管理")
public class TranscodeTaskInfo extends BaseModel {

    /**
     * 任务ID，自增
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("任务ID，自增")
    private Integer transcodeTaskId;
    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String transcodeTaskName;
    /**
     * 任务类型 0常规任务 1直播监看任务 没有转码规则时，直播监看请求生成任务，每个用户ID只能有一个直播监看任务
     */
    @ApiModelProperty("任务类型 0常规任务 1直播监看任务 没有转码规则时，直播监看请求生成任务，每个用户ID只能有一个直播监看任务")
    private Integer taskMode;
    /**
     * 节目ID
     */
    @ApiModelProperty("节目ID")
    private String signalId;
    /**
     * 信源地址
     */
    @ApiModelProperty("信源地址")
    private String sourceURL;
    /**
     * 转码规则ID
     */
    @ApiModelProperty("转码规则ID")
    private Integer transcodeRuleId;
    /**
     * 转码服务编号
     */
    @ApiModelProperty("转码服务编号")
    private String serviceCode;
    /**
     * 转码服务名称
     */
    @ApiModelProperty("转码服务名称")
    private String serviceName;
    /**
     * GPU加速：0不开启，1开启
     */
    @ApiModelProperty("GPU加速：0不开启，1开启")
    private Integer useGPU;
    /**
     * 信源输入网口
     */
    @ApiModelProperty("信源输入网口")
    private String inputIP;
    /**
     * 转码输出网口
     */
    @ApiModelProperty("转码输出网口")
    private String outputIP;
    /**
     * 运行状态
     */
    @ApiModelProperty("运行状态")
    private Integer taskStatus;
    /**
     * 状态：0:删除、1:正常、2:停用
     */
    @ApiModelProperty("状态：0:删除、1:正常、2:停用")
    private Integer isDelete;

    @TableField(exist = false)
    private Integer result;
    @TableField(exist = false)
    private String UDPURL;
    @TableField(exist = false)
    private String HTTPTSURL;
    @TableField(exist = false)
    private String HLSURL;
    @TableField(exist = false)
    private String RTMPURL;
    @TableField(exist = false)
    private String deviceIds;
    @TableField(exist = false)
    private String osdSwitch;
    @TableField(exist = false)
    private TranscodeRuleInfo transcodeRuleInfo;
    @TableField(exist = false)
    private String inputUrl;
    @TableField(exist = false)
    private List<TranscodeTaskStreamRel> transcodeTaskStreamRelList;
    @TableField(exist = false)
    private String transcodeTaskIds;
    @TableField(exist = false)
    private Integer isAll;
    @TableField(exist = false)
    private Integer operation;

    @TableField(exist = false)
    private String channelIds;
    @TableField(exist = false)
    private String transcodeRuleIds;
    @TableField(exist = false)
    private String transcodeServiceIds;
    @TableField(exist = false)
    private Integer transcodeClusterId;

}