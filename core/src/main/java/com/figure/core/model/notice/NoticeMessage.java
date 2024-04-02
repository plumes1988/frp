package com.figure.core.model.notice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.sse.SseEmitterWrapper;
import com.figure.core.sse.SseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * <p>
 * 通知消息表
 * </p>
 *
 * @author jobob
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notice_message")
public class NoticeMessage extends SseMessage implements Serializable {


    /**
     * 来源类型 1: 设备操作 2:设备报警 3:信号报警
     */
    public static String DEVICE_CONTROL  = "1";
    public static String DEVICE_ALARM = "2";
    public static String SIGNAL_ALARM  = "3";
    public static String DEVICE_STATUS_CHANGE = "4";


    /**
     * 媒介类型 1:平台通知 2:邮件通知 3:短信通知
     */
    public static String WEB  = "1";
    public static String EMAIL = "2";
    public static String SMS  = "3";

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer messageId;

    /**
     * 媒介类型 1:平台通知 2:邮件通知 3:短信通知
     */
    @TableField("agentType")
    private String agentType;

    /**
     * 来源类型 1: 设备操作 2:设备报警 3:信号报警 4:设备状态指标变更
     */
    @TableField("sourceType")
    private String sourceType;

    /**
     * 来源ID
     */
    @TableField("sourceId")
    private String sourceId;

    /**
     * 消息
     */
    @TableField("msg")
    private String msg;

    /**
     * 级别
     */
    @TableField("level")
    private String level;

    /**
     * 0:unread,1:hasread
     */
    @TableField("readStatus")
    private String readStatus;

    /**
     * 接收人用户Id
     */
    @TableField("receiverUserId")
    private Integer receiverUserId;

    /**
     * 0 : 发送失败；1 : 发送成功
     */
    @TableField("status")
    private Integer status;

    /**
     * 错误原因
     */
    @TableField("errorCause")
    private String errorCause;

    /**
     * 来源发生时间
     */
    @TableField("sourceHappendTime")
    private Date sourceHappendTime;

    @Override
    public boolean matchParams(SseEmitterWrapper sseEmitterWrapper) {
        Map<String, Object> params = sseEmitterWrapper.getParams();
        String userId = params.get("userId").toString();
        String userType = params.get("userType").toString();
        boolean result = false;
        if(userId.equals(receiverUserId.toString())){
            result = true;
        }
        return result;
    }


    /**
     * 用户名
     */
    @TableField(exist = false)
    private String receiverUserName;

    /**
     * 电话号码
     */
    @TableField(exist = false)
    private String telephone;

    /**
     * 电子邮件
     */
    @TableField(exist = false)
    private String email;
}
