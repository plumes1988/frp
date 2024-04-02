package com.figure.core.model.notification;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notification_entrance_data")
public class NotificationEntranceData implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 事件
     */
    @TableField("event")
    private String event;

    /**
     * 用户
     */
    @TableField("user")
    private String user;

    /**
     * 发送模式
     */
    @TableField("sendMode")
    private String sendMode;

    /**
     * 消息信息
     */
    @TableField("msg")
    private String msg;

    /**
     * 发送状态(0:"未发送",1:"成功",2:"失败",)
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("creationTime")
    private LocalDateTime creationTime;


}
