package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色监测站关联表
 * </p>
 *
 * @author jobob
 * @since 2023-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_front_station_rel")
public class SysRoleFrontStationRel implements Serializable {

    /**
     * 角色Id
     */
    @TableField("roleId")
    private Integer roleId;

    /**
     * 监测站Id
     */
    @TableField("frontId")
    private Integer frontId;

    /**
     * 设备可控：1:可控；0:不可控
     */
    @TableField("controllable")
    private Integer controllable;

}
