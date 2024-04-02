package com.figure.core.model.front;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import com.figure.core.util.copycat.annotaion.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2021-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("front_station_info")
public class FrontStationInfo extends BaseModel implements Serializable {


    /**
     * 前端ID
     */
    @TableId(value = "frontId", type = IdType.AUTO)
    private Integer frontId;

    /**
     * 前端名称
     */
    @TableField("frontName")
    private String frontName;

    /**
     * 上级前端ID
     */
    @TableField("supfrontId")
    private Integer supfrontId;

    /**
     * 前端工作模式：0常规监测前端，1黑广播监管前端，2中波压制监管前端
     */
    @TableField("workMode")
    private Integer workMode;

    /**
     * 前端类型 1.区域前端；2.非区域前端
     */
    @TableField("frontType")
    private String frontType;

    /**
     * 行政级别：0：全国 1：省 2：市  3：区/县，99:无行政级别归属
     */
    @TableField("regionalLevel")
    private Integer regionalLevel;

    /**
     * 所属行政区域ID
     */
    @TableField("regionId")
    private Integer regionId;

    /**
     * 经度，可为空，为空就用所属行政区域经纬度
     */
    @TableField("frontLat")
    private String frontLat;

    /**
     * 纬度，可为空，为空就用所属行政区域经纬度
     */
    @TableField("frontLong")
    private String frontLong;

    /**
     * 前端编号，固定5位
     */
    @TableField("frontCode")
    private String frontCode;

    /**
     * 前端地址
     */
    @TableField("frontLocation")
    private String frontLocation;

    /**
     * 前端管理服务IP
     */
    @TableField("frontIP")
    private String frontIP;

    /**
     * 前端登录名
     */
    @TableField("loginName")
    private String loginName;

    /**
     * 前端登录密码
     */
    @TableField("loginPass")
    private String loginPass;

    /**
     * 最大带宽Mbps
     */
    @TableField("bandWidth")
    private String bandWidth;

    /**
     * 媒体图片存储路径
     */
    @TableField("mediaImgPath")
    private String mediaImgPath;

    /**
     * 前端关联信号类型，所有前端具有信号类型逗号分隔
     */
    @TableField("signalCodes")
    private String signalCodes;

    /**
     * 创建人员ID
     */
    @TableField("createUserId")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新人员ID
     */
    @TableField("updateUserId")
    private Integer updateUserId;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 0：停用、1：启用
     */
    @TableField("isEnable")
    private Integer isEnable;

    /**
     * 0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑
     */
    @TableField("isDelete")
    private Integer isDelete;

    @TableField(exist = false)
    private String delay = "离线";


}
