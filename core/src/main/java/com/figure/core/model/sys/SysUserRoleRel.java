package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 用户角色关键
 * </p>
 *
 *@author feather
 *@date 2021-03-18 16:07:45
 */
@Data
@Accessors(chain = true)
@TableName("sys_user_role_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUserRoleRel用户角色关键", description = "用户角色关键")
public class SysUserRoleRel {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Long roleId;

}