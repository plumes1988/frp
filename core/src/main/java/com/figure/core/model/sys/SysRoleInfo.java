package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 角色信息
 * </p>
 *
 *@author feather
 *@date 2021-03-18 16:07:45
 */
@Data
@Accessors(chain = true)
@TableName("sys_role_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysRoleInfo角色信息", description = "角色信息")
public class SysRoleInfo extends BaseModel {

    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("角色ID")
    private Long roleId;
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String roleNotes;
    /**
     * 权值
     */
    @ApiModelProperty("权值")
    private Integer qxValue;
    /** 创建人员ID */

    @TableField(exist = false)
    private List<Integer> frontIds = new ArrayList<>();

    @TableField(exist = false)
    private List<Integer> controllableFrontIds = new ArrayList<>();

    @TableField(exist = false)
    private List<String> logicChannelCodes = new ArrayList<>();

    @TableField(exist = false)
    private List<Integer> deviceIds = new ArrayList<>();

    @TableField(exist = false)
    private List<Integer> permissionIds = new ArrayList<>();

    @TableField(exist = false)
    private Integer controllable = 0;

}