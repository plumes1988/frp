package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 报警类型表
 * </p>
 *
 * @author jobob
 * @since 2022-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_alarmtype_info")
public class DeviceAlarmtypeInfo implements Serializable {


    /**
     * 报警类型ID
     */
    @TableId(value = "alarmTypeId", type = IdType.INPUT)
    private Integer alarmTypeId;

    @TableField(exist = false)
    private Integer oldAlarmTypeId;

    /**
     * 报警类型名称
     */
    @TableField("alarmTypeName")
    private String alarmTypeName;

    /**
     * 报警类型别名
     */
    @TableField("alarmTypeAlias")
    private String alarmTypeAlias;

    /**
     * 报警来源1：设备 2：动力 3：环境
     */
    @TableField("alarmSource")
    private Integer alarmSource;

    /**
     * 是否启用
     */
    @TableField("isEnable")
    private Integer isEnable;

    /**
     * 是否删除
     */
    @TableField("isDelete")
    private Integer isDelete;


}
