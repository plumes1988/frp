package com.figure.op.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 * 登录信息对象
 * @author ly
 */
@Data
public class SysUserLoginVo   {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
