package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 系统运行状态记录表
 * </p>
 *
 * @author jobob
 * @since 2023-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_os_status_record")
public class SysOsStatusRecord implements Serializable {


    /**
     * 类型 1:cpu 2:jvm 3:net recv 4: net send
     */
    @TableField("type")
    private Integer type;

    /**
     * 记录时间
     */
    @TableField("time")
    private Date time;

    /**
     * 记录值
     */
    @TableField("value")
    private String value;


}
