package com.figure.core.model.notice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 报警触发通知规则
 * </p>
 *
 * @author jobob
 * @since 2022-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notice_alarm_trigger_rule")
public class NoticeAlarmTriggerRule implements Serializable {


    /**
     * 规则ID
     */
    @TableId(value = "ruleId", type = IdType.AUTO)
    private Integer ruleId;

    /**
     * 规则名称
     */
    @TableField("ruleName")
    private String ruleName;

    /**
     * 关联通知策略ID
     */
    @TableField("strategyId")
    private Integer strategyId;

    /**
     * 报警对象类型: 1:设备;2:信号；3:节目
     */
    @TableField("alarmObjectType")
    private String alarmObjectType;

    /**
     * 报警对象列表：设备id ; 码流id；节目id
     */
    @TableField("alarmObjectIds")
    private String alarmObjectIds;

    /**
     * 指标编号 或者 报警类型ID
     */
    @TableField("alarmTypes")
    private String alarmTypes;

    /**
     * 参数配置
     */
    @TableField("settings")
    private String settings;

    @Data
    public static class Param{
        private Integer deviceId;
        private String deviceCode;
        private Integer[] alarmTypeIds;
        private String[] indicatorCodes;
    }

    @Data
    public static class Setting{
        private Integer type;
        private Integer noticeMessageTempleteId;
        private Param[] params;
    }

    @TableField(exist = false)
    private List<Setting> settings_;

}
