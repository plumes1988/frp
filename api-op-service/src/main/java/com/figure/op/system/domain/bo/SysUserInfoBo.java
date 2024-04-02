package com.figure.op.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author fsn
 */
@Data
public class SysUserInfoBo extends BaseEntity {

    /**
     * 用户ID
     */
    @TableId(value = "userId")
    private Integer userId;

    /**
     * 姓名
     */
    @TableField("userName")
    private String userName;

    /**
     * 登录名
     */
    @TableField("loginName")
    private String loginName;

    /**
     * 工号
     */
    @TableField("userCode")
    private String userCode;

    /**
     * 密码
     */
    @TableField("loginPass")
    private String loginPass;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;


}
