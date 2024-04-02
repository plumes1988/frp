package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
/**
 * <p>
 * 信源调度服务
 * </p>
 *
 * @author jobob
 * @since 2022-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("signal_source_change_service")
public class SignalSourceChangeService implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 服务编号
     */
    @TableField("serviceCode")
    private String serviceCode;

    /**
     * 服务名称
     */
    @TableField("serviceName")
    private String serviceName;

    /**
     * 矩阵状态更新频率，单位毫秒
     */
    @TableField("updateTime")
    private Integer updateTime;

    /**
     * 关联设备ID
     */
    @TableField("deviceId")
    private Integer deviceId;

}
