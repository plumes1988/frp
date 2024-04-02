package com.figure.core.model.audit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * <p>
 * 自动技审策略
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("audit_auto_strategy")
public class AuditAutoStrategy implements Serializable {


    /**
     * 策略ID
     */
    @TableId(value = "strategyId",type= IdType.AUTO)
    private Integer id;

    /**
     * 策略名称
     */
    @TableField("strategyName")
    private String strategyName;

    /**
     * 策略描述
     */
    @TableField("strategyDescribe")
    private String strategyDescribe;

    /**
     * 关联频道
     */
    @TableField("channelRel")
    private Integer channelRel;

    /**
     * 优先级：0普通、1较高、2最高
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 策略时间规则类型：0每天、1每周、2每月
     */
    @TableField("timingMode")
    private Integer timingMode;

    /**
     * 策略循环间隔：每X天、每X周、每X月
     */
    @TableField("repeatSpan")
    private Integer repeatSpan;

    /**
     * 周内日期选择
     */
    @TableField("dayOfWeek")
    private String dayOfWeek;

    /**
     * 每月循环模式：0按天，每月第几天，2按周，每月第几周
     */
    @TableField("repeatMonthMode")
    private Integer repeatMonthMode;

    /**
     * 每月第几天，每月第几周
     */
    @TableField("repeatOfMonth")
    private String repeatOfMonth;

    /**
     * 工作日是否循环：0单次执行、1循环执行
     */
    @TableField("workRepeatMode")
    private Integer workRepeatMode;

    /**
     * 策略执行日内开始执行时间
     */
    @TableField("workStartTime")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time workStartTime;

    /**
     * 策略执行日内结束执行时间
     */
    @TableField("workEndTime")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time workEndTime;

    /**
     * 循环间隔，单位分钟，页面可选单位：分钟、小时，入库转换为分钟
     */
    @TableField("workRepeatSpan")
    private Integer workRepeatSpan;

    /**
     * 策略开始日期
     */
    @TableField("startDate")
    private Date startDate;

    /**
     * 策略结束日期
     */
    @TableField("endDate")
    private Date endDate;

    /**
     * 永久有效：0非永久有效、1永久有效
     */
    @TableField("isForever")
    private Integer isForever;

    /**
     * 启用状态：0停用、1启用
     */
    @TableField("isEnable")
    private Integer isEnable;

    /**
     * 创建人员ID
     */
    @TableField("createUserId")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新人员ID
     */
    @TableField("updateUserId")
    private Integer updateUserId;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 是否删除：0未删除、1已删除
     */
    @TableField("isDelete")
    private Integer isDelete;

    @TableField(exist = false)
    private Integer parentId;


    public AuditAutoStrategy() {
        super();
    }

    public AuditAutoStrategy(Integer id, String strategyName, Integer parentId) {
        this.id = id;
        this.strategyName = strategyName;
        this.parentId = parentId;
    }
}
