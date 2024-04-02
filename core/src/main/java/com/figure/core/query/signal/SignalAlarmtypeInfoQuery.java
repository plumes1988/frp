package com.figure.core.query.signal;

import com.figure.core.model.signal.SignalAlarmtypeInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.NotIn;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SignalAlarmtypeInfoQuery extends AbstractQuery<SignalAlarmtypeInfo> {

    @Eq
    @ApiModelProperty("默认报警信息主键Id")
    private Integer id;

    @Eq
    @ApiModelProperty("报警信息名称")
    private String alarmName;

    @Eq
    @ApiModelProperty("默认报警信息编号")
    private Integer number;

    @Eq
    @ApiModelProperty("判定方式 0:不等于 1:大于等于 2:小于等于 3:等于")
    private String judgementType;

    @Eq
    @ApiModelProperty("异态量化阈值")
    private Integer thresholdValue;

    @Eq
    @ApiModelProperty("量化偏移范围")
    private Integer thresholdOffset;

    @Eq
    @ApiModelProperty("判定次数")
    private Integer videoDegradation;

    @Eq
    @ApiModelProperty("异态类型 1音频异常 2视频异常 3传输异常 4音频比对 5视频比对")
    private Integer alarmType;

    @Eq
    @ApiModelProperty("是否直接判定  1：是，传输异态，无需比对直接判定；0：否，内容层报警依据比对判定")
    private Integer judgement;

    @Eq
    @ApiModelProperty("1:是 0:否 有些报警发生仅提示，不作为信号择优条件")
    private Integer switchCondition;

    @Eq
    @ApiModelProperty("报警等级1-100")
    private Integer alarmLevel;

    @Eq
    @ApiModelProperty("事故类型 1:停播事故 2:劣播事故")
    private String eventType;

    @Eq
    @ApiModelProperty("分析延时，单位：毫秒 当前如果是比对评分时作为比对异态的恢复判定延时")
    private Integer analysisDelay;

    @Eq
    @ApiModelProperty("报警延时，单位：秒 默认：5秒 对于未参与比对的节目进行异态判定")
    private Integer alarmDelay;

    @Eq
    @ApiModelProperty("恢复延时，单位：秒 默认：2秒 对于未参与比对的节目进行异态恢复判定")
    private Integer recoveryDelay;

    @Eq
    @ApiModelProperty("超时阈值")
    private Integer timeoutThreshold;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    @NotIn(alias = "isDelete")
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private List<Integer> notIsDelete;
}