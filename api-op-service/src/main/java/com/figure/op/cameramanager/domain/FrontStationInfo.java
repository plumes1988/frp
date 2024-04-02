package com.figure.op.cameramanager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author liqiang
 * @Date 2023/9/8 17:15
 * @Version 1.5
 * 站点信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("front_station_info")
public class FrontStationInfo extends BaseEntity {
    /**
     * 站点Id
     */
    @TableId("frontId")
    private Integer frontId;

    /**
     * 前端名称
     */
    @TableField("frontName")
    private String frontName;

    /**
     * 上级前端Id
     */
    @TableField("supFrontId")
    private Integer supFrontId;

    /**
     * 前端工作模式：0常规监测前端，1黑广播监管前端，2中波压制监管前端
     */
    @TableField("workMode")
    private Integer workMode;


    /**
     * 前端类型 1.区域前端；2.非区域前端
     */
    @TableField("frontType")
    private Integer frontType;

    /**
     * 所属行政区域ID
     */
    @TableField("regionId")
    private Integer regionId;

    /**
     * 行政级别：0：全国 1：省 2：市  3：区/县，99:无行政级别归属
     */
    @TableField("regionalLevel")
    private Integer regionalLevel;

    /**
     * 经度，可为空，为空就用所属行政区域经纬度
     */
    @TableField("frontLat")
    private Integer frontLat;

    /**
     * 纬度，可为空，为空就用所属行政区域经纬度
     */
    @TableField("frontLong")
    private Integer frontLong;

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
     * 直播图片存储路径
     */
    @TableField("mediaImgPath")
    private String mediaImgPath;

    /**
     * 前端关联信号类型，所有前端具有信号类型逗号分隔
     */
    @TableField("signalCodes")
    private String signalCodes;

    /**
     * 0：停用、1：启用
     */
    @TableField("isEnable")
    private String isEnable;



}
