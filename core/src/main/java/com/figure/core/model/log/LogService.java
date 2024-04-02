package com.figure.core.model.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.figure.core.sse.SseEmitterWrapper;
import com.figure.core.sse.SseMessage;
import com.figure.core.util.copycat.annotaion.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 服务日志
 * </p>
 *
 * @author jobob
 * @since 2024-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("log_service")
public class LogService extends SseMessage implements Serializable{

    /*
    / 服务类型 start
     */
    public static Integer SERVICE_TYPE_COLLECT = 1; //采集
    public static Integer SERVICE_TYPE_SPECTRUM_MONITOR = 2; //频谱监测
    public static Integer SERVICE_TYPE_REDIS = 3; //redis
    public static Integer SERVICE_TYPE_ROCKTMQ = 4; //rocketmq
    public static Integer SERVICE_TYPE_PLATFORM = 5; //网管平台
    public static Integer SERVICE_TYPE_MYSQL = 6; // mysql
    /*
    / 服务类型 end
     */

    public static String SOURCE_SERVICE_REIDS = "redis";
    public static String SOURCE_SERVICE_ROCKTMQ = "rocketmq";
    public static String SOURCE_SERVICE_PLATFORM = "网管平台";
    public static String SOURCE_SERVICE_MYSQL = "mysql";


    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    /**
     * 来源应用
     */
    @TableField("sourceService")
    private String sourceService;

    /**
     * 应用类型
     */
    @TableField("serviceType")
    private Integer serviceType;

    /**
     * 时间戳
     */
    @TableField("timestamp")
    private Date timestamp;

    /**
     * 日志级别
     */
    @TableField("level")
    private String level;

    /**
     * 日志内容
     */
    @TableField("message")
    private String message;


    @Override
    public boolean matchParams(SseEmitterWrapper sseEmitterWrapper) {
        return true;
    }
}
