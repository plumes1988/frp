package com.figure.core.model.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2024-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("log_login")
public class LogLogin implements Serializable {

    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    /**
     * 用户Id
     */
    @TableField("userId")
    private Long userId;


    @TableField("userName")
    private String userName;

    /**
     * 登录IP地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 登录时间
     */
    @TableField("time")
    private Date time;

    /**
     * 登录设备信息
     */
    @TableField("device")
    private String device;

    /**
     * 登录结果
     */
    @TableField("result")
    private Integer result;

    /**
     * 登录地点
     */
    @TableField("address")
    private String address;

}
