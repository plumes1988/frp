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
 * 通知媒介管理
 * </p>
 *
 * @author jobob
 * @since 2022-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notice_agent")
public class NoticeAgent implements Serializable {


    /**
     * 媒介ID
     */
    @TableId(value = "agentId", type = IdType.AUTO)
    private Integer agentId;

    /**
     * 媒介名称
     */
    @TableField("name")
    private String name;

    /**
     * 媒介类型
     */
    @TableField("type")
    private String type;

    /**
     * 媒介参数
     */
    @TableField("config")
    private String config;


}
