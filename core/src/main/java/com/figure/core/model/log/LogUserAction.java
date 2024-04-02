package com.figure.core.model.log;

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
 * 系统用户日志表
 * </p>
 *
 * @author jobob
 * @since 2024-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("log_user_action")
public class LogUserAction implements Serializable {


    /**
     * 日志ID
     */
    @TableId(value = "logId", type = IdType.AUTO)
    private Integer logId;

    /**
     * 操作者用户ID
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 操作时间
     */
    @TableField("optTime")
    private Date optTime;

    /**
     * IP地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 状态码
     */
    @TableField("state")
    private String state;

    /**
     * 耗时
     */
    @TableField("time")
    private Integer time;

    /**
     * 描述:接口名说明
     */
    @TableField("describes")
    private String describes;

    /**
     * 端点入口, api请求path
     */
    @TableField("endpoint")
    private String endpoint;


}
