package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 角色模块关系
 * </p>
 *
 *@author feather
 *@date 2021-03-18 16:07:45
 */
@Data
@Accessors(chain = true)
@TableName("sys_role_module_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysRoleModuleRel角色模块关系", description = "角色模块关系")
public class SysRoleModuleRel {

    /**
     * 角色Id
     */
    @ApiModelProperty("角色Id")
    private Long roleId;
    /**
     * 模块Id
     */
    @ApiModelProperty("模块Id")
    private Long moduleId;
    /**
     * 权值：1看、2改、4操作（可以做与运算）
     */
    @ApiModelProperty("权值：1看、2改、4操作（可以做与运算）")
    private Integer permission;

    @TableField(exist = false)
    private String moduleids;

    @TableField(exist = false)
    private String permissions;

}