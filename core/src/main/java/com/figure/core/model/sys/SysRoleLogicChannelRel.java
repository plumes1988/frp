package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色播出系统 ( 逻辑频道 ) 关联表
 * </p>
 *
 * @author jobob
 * @since 2023-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_logic_channel_rel")
public class SysRoleLogicChannelRel implements Serializable {


    /**
     * 角色ID
     */
    @TableField("roleId")
    private Integer roleId;

    /**
     * 逻辑频道编号
     */
    @TableField("logicChannelCode")
    private String logicChannelCode;


}
