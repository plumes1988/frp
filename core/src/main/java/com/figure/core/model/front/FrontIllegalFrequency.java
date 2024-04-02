package com.figure.core.model.front;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.figure.core.base.BaseModel;


/**
 * <p>
 * 黑广播业务配置
 * </p>
 *
 *@author feather
 *@date 2021-05-19 17:16:25
 */
@Data
@Accessors(chain = true)
@TableName("front_illegal_frequency")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FrontIllegalFrequency黑广播业务配置", description = "黑广播业务配置")
public class FrontIllegalFrequency extends BaseModel {

    /** 前端ID */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("前端ID")
    private Integer frontId;
    /** 新频自动录制： 0 关闭   1 打开 */
    @ApiModelProperty("新频自动录制： 0 关闭   1 打开")
    private String recordNewFreSwitch;
    /** 是否录制白名单频点： 0 否   1是 */
    @ApiModelProperty("是否录制白名单频点： 0 否   1是")
    private String recordWhitelistSwitch;
    /** 是否循环录制： 0 关闭   1 打开 */
    @ApiModelProperty("是否循环录制： 0 关闭   1 打开")
    private String looprecordIllegalSwitch;
    /** 非法频点单次收录时长,单位:秒,默认1200 */
    @ApiModelProperty("非法频点单次收录时长,单位:秒,默认1200")
    private Long illegalRecordDuration;
    /** 非法频率报警延时, 单位:秒 默认300 */
    @ApiModelProperty("非法频率报警延时, 单位:秒 默认300")
    private Long illegalAlarmDelay;
    /** 非法频率报警恢复延时, 单位:秒 */
    @ApiModelProperty("非法频率报警恢复延时, 单位:秒")
    private Long illegalAlarmRecoveryDelay;
    /** 是否启用频谱仪： 0 关闭   1 打开 */
    @ApiModelProperty("是否启用频谱仪： 0 关闭   1 打开")
    private String useSpectrum;
    /** 关联频谱仪设备Id，用于获取频谱仪IP 和 频谱仪所属采集点ID */
    @ApiModelProperty("关联频谱仪设备Id，用于获取频谱仪IP 和 频谱仪所属采集点ID")
    private Long spectrumId;
    /** 非法频率识别阈值 */
    @ApiModelProperty("非法频率识别阈值")
    private Long spectrumCardinals;
    /** 频谱收录开关 0关闭 1打开，只有启用频谱仪时有效 */
    @ApiModelProperty("频谱收录开关 0关闭 1打开，只有启用频谱仪时有效")
    private String spectrumRecord;
    /** hls，单位：秒 */
    @ApiModelProperty("频谱收录时间间隔，单位：秒")
    private Long spectrumRecordInterval;

}