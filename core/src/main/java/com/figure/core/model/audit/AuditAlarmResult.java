package com.figure.core.model.audit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 技审异态结果
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("audit_alarm_result")
public class AuditAlarmResult implements Serializable {

    /**
     * 素材ID
     */
    @TableId("clipId")
    private Integer id;

    /**
     * 异态ID
     */
    @TableField("alarmId")
    private Integer alarmId;

    /**
     * 异态入点
     */
    @TableField("trimIn")
    private Integer trimIn;

    /**
     * 异态时长
     */
    @TableField("alarmDuration")
    private Integer alarmDuration;

    /**
     * 分析数据
     */
    @TableField("analysisData")
    private String analysisData;

    /**
     * 审核状态：0未审核、1通过、2未通过
     */
    @TableField("auditStatus")
    private Integer auditStatus;


    @TableField(exist = false)
    private String clipName;

    @TableField(exist = false)
    private String alarmName;

    @TableField(exist = false)
    private String channelName;

}
