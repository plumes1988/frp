package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <p>
 * 角色业务权限关联表
 * </p>
 *
 * @author jobob
 * @since 2023-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_permission_rel")
public class SysRolePermissionRel implements Serializable {

    @Id
    @GeneratedValue
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    @TableField("roleId")
    private Integer roleId;

    /**
     * 权限id
     */
    @TableField("permissionId")
    private Integer permissionId;


}
