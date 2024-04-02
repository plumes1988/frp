package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色播出系统 ( 逻辑频道 ) 关联表
 * </p>
 *
 * @author jobob
 * @since 2023-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_device_rel")
public class SysRoleDeviceRel implements Serializable {




    /**
     * 角色ID
     */
    @TableField("roleId")
    private Integer roleId;

    /**
     * 设备ID
     */
    @TableField("deviceId")
    private Integer deviceId;


}
