package com.figure.core.model.visual;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 设备链路图表
 * </p>
 *
 * @author jobob
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("visual_chain")
public class VisualChain implements Serializable {


    /**
     * 链路图Id
     */
    @TableId(value = "chainId", type = IdType.AUTO)
    private Integer chainId;

    /**
     * 链路图名称
     */
    @TableField("chainName")
    private String chainName;

    /**
     * 所属站点Id
     */
    @TableField("frontId")
    private Integer frontId;

    /**
     * 链路属性: 1,设备链路; 2,信号链路
     */
    @TableField("chainType")
    private Integer chainType;

    /**
     * 报警屏蔽状态: 1屏蔽; 0不屏蔽
     */
    @TableField("alarmFilterStatus")
    private Integer alarmFilterStatus;

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

    @TableField("img")
    private String img;

    @TableField("json")
    private String json;

    @TableField("channelIdsMap")
    private String channelIdsMap;

    @TableField("deiveIdsMap")
    private String deiveIdsMap;

    @TableField("groupName")
    private String groupName;

}
