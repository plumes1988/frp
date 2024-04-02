package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>
 * 第三方调用key管理
 * </p>
 *
 * @author jobob
 * @since 2023-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_api_key")
public class SysApiKey implements Serializable {

    @Id
    @GeneratedValue
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * api调用方
     */
    @TableField("name")
    private String name;

    /**
     * 通过key获取token
     */
    @TableField("apiKey")
    private String apiKey;

    /**
     * 过期时长(小时)
     */
    @TableField("expiration")
    private Integer expiration;

    /**
     * 状态 0:禁用  1:启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 备注说明
     */
    @TableField("remark")
    private String remark;


}
