package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2024-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_jwt_userInfo_store")
public class SysJwtUserinfoStore implements Serializable {

    /**
     * token
     */
    @TableId(value="jwt",type= IdType.INPUT)
    private String jwt;

    /**
     * json用户信息
     */
    @TableField("tUserInfoJson")
    private String tUserInfoJson;


}
