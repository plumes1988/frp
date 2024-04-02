package com.figure.core.model.notice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 通知策略管理
 * </p>
 *
 * @author jobob
 * @since 2022-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notice_strategy")
public class NoticeStrategy implements Serializable {


    /**
     * 策略ID
     */
    @TableId(value = "strategyId", type = IdType.AUTO)
    private Integer strategyId;

    /**
     * 策略名称
     */
    @TableField("name")
    private String name;

    /**
     * 媒介IDS
     */
    @TableField("agentIds")
    private String agentIds;

    /**
     * 通知对象:用户组IDS
     */
    @TableField("groupIds")
    private String groupIds;

    /**
     * 策略描述
     */
    @TableField("mark")
    private String mark;


}
