package com.figure.core.model.signal;

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


/**
 * <p>
 * 默认报警类型信息
 * </p>
 *
 * @author feather
 * @date 2023-03-07 16:00:32
 */
@Data
@Accessors(chain = true)
@TableName("signal_alarmtype_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalAlarmTypeInfo", description = "默认报警类型信息")
public class SignalAlarmtypeInfo extends BaseModel {

    /**
     * 默认报警信息主键Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("默认报警信息主键Id")
    private Integer id;
    /**
     * 报警信息名称
     */
    @ApiModelProperty("报警信息名称")
    private String alarmName;
    /**
     * 默认报警信息编号
     */
    @ApiModelProperty("默认报警信息编号")
    private Integer number;
    /**
     * 判定方式 0:不等于 1:大于等于 2:小于等于 3:等于
     */
    @ApiModelProperty("判定方式 0:不等于 1:大于等于 2:小于等于 3:等于")
    private Integer judgementType;
    /**
     * 异态量化阈值
     */
    @ApiModelProperty("异态量化阈值")
    private Integer thresholdValue;
    /**
     * 量化偏移范围
     */
    @ApiModelProperty("量化偏移范围")
    private Integer thresholdOffset;
    /**
     * 判定次数
     */
    @ApiModelProperty("判定次数")
    private Integer videoDegradation;
    /**
     * 异态类型 1音频异常 2视频异常 3传输异常 4音频比对 5视频比对
     */
    @ApiModelProperty("异态类型 1音频异常 2视频异常 3传输异常 4音频比对 5视频比对")
    private Integer alarmType;
    /**
     * 是否直接判定  1：是，传输异态，无需比对直接判定；0：否，内容层报警依据比对判定
     */
    @ApiModelProperty("是否直接判定  1：是，传输异态，无需比对直接判定；0：否，内容层报警依据比对判定")
    private Integer judgement;
    /**
     * 1:是 0:否 有些报警发生仅提示，不作为信号择优条件
     */
    @TableField(value = "switchCondition")
    @ApiModelProperty("1:是 0:否 有些报警发生仅提示，不作为信号择优条件")
    private Integer switchCondition;
    /**
     * 报警等级1-100
     */
    @ApiModelProperty("报警等级1-100")
    private Integer alarmLevel;
    /**
     * 事故类型 1:停播事故 2:劣播事故
     */
    @ApiModelProperty("事故类型 1:停播事故 2:劣播事故")
    private Integer eventType;
    /**
     * 分析延时，单位：毫秒 当前如果是比对评分时作为比对异态的恢复判定延时
     */
    @ApiModelProperty("分析延时，单位：毫秒 当前如果是比对评分时作为比对异态的恢复判定延时")
    private Integer analysisDelay;
    /**
     * 报警延时，单位：秒 默认：5秒 对于未参与比对的节目进行异态判定
     */
    @ApiModelProperty("报警延时，单位：秒 默认：5秒 对于未参与比对的节目进行异态判定")
    private Integer alarmDelay;
    /**
     * 恢复延时，单位：秒 默认：2秒 对于未参与比对的节目进行异态恢复判定
     */
    @ApiModelProperty("恢复延时，单位：秒 默认：2秒 对于未参与比对的节目进行异态恢复判定")
    private Integer recoveryDelay;
    /**
     * 超时阈值
     */
    @ApiModelProperty("超时阈值")
    private Integer timeoutThreshold;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}