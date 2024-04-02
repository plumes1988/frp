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

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息
 * </p>
 *
 *@author feather
 *@date 2021-03-18 16:07:45
 */
@Data
@Accessors(chain = true)
@TableName("sys_user_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUserInfo用户信息", description = "用户信息")
public class SysUserInfo extends BaseModel {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String userName;
    /**
     * 登录名
     */
    @ApiModelProperty("登录名")
    private String loginName;
    /**
     * 工号
     */
    @ApiModelProperty("工号")
    private String userCode;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String loginPass;
    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    private String telephone;
    /** 电子邮件 */
    @ApiModelProperty("电子邮件")
    private String email;
    /** 用户头像图片url地址 */
    @ApiModelProperty("用户头像图片url地址")
    private String imgUrl;
    /** 用户所属，数据字典 */
    @ApiModelProperty("用户所属，数据字典")
    private Long userFrom;
    /** sessionId */
    @ApiModelProperty("sessionId")
    private String sessionId;
    /** 连续登陆错误次数 */
    @ApiModelProperty("连续登陆错误次数")
    private Long errorTimes;
    /** 最后登陆时间 */
    @ApiModelProperty("最后登陆时间")
    private Date lastLoginTime;
    /** 0-不锁定；1-锁定，锁定用户输入正确用户名密码也不可以登录，需要联系管理员解锁 */
    @ApiModelProperty("0-不锁定；1-锁定，锁定用户输入正确用户名密码也不可以登录，需要联系管理员解锁")
    private Integer lockStatus;
    /** 主题 */
    @ApiModelProperty("主题")
    private String complexion;
    /** 导航模式 */
    @ApiModelProperty("导航模式")
    private Integer navigationMode;
    /** 个人主页 */
    @ApiModelProperty("个人主页")
    private String homepage;
    /** 每页数据条目数 */
    @ApiModelProperty("每页数据条目数")
    private Integer dataListSize;
    /** 0：停用,1：启用 */
    @ApiModelProperty("0：停用,1：启用")
    private Integer isEnable;
    /** 主题配置 */
    @ApiModelProperty("主题配置")
    private String theme;
    /** 用户类型：0:普通用户；1:超级管理员*/
    @ApiModelProperty("用户类型")
    private Integer type;

    @TableField(exist = false)
    private List<Integer> roleIds;

    @TableField(exist = false)
    private String roleNames;

    @TableField(exist = false)
    private Integer ignoreRole = 0;

}