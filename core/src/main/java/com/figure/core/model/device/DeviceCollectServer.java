package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 网管采集服务管理表
 * </p>
 *
 * @author jobob
 * @since 2022-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_collect_server")
public class DeviceCollectServer implements Serializable {


    /**
     * 服务Id
     */
    @TableId(value = "serverId", type = IdType.AUTO)
    private Integer serverId;

    /**
     * 服务编号
     */
    @TableField("serverCode")
    private String serverCode;

    /**
     * 服务名称
     */
    @TableField("serverName")
    private String serverName;

    /**
     * 关联服务器ID
     */
    @TableField("deviceId")
    private String deviceId;

    /**
     * 管理口IP
     */
    @TableField("controlIP")
    private String controlIP;

    /**
     * 数据网口
     */
    @TableField("dataIP")
    private String dataIP;

    /**
     * 最后心跳时间
     */
    @TableField("heartbeatTime")
    private Date heartbeatTime;

    /**
     * 服务健康状态检测端口
     */
    @TableField("checkPort")
    private Integer checkPort;

    public String getFullName(){
        return "["+this.getServerCode()+"]"+this.getServerName();
    }

    @TableField(exist = false)
    private Integer isOnline;

}
