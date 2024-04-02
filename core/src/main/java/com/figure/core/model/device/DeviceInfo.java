package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备信息
 * </p>
 *
 * @author jobob
 * @since 2022-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_info")
public class DeviceInfo extends BaseModel implements Serializable {


    /**
     * 设备Id
     */
    @TableId(value = "deviceId", type = IdType.AUTO)
    private Integer deviceId;

    /**
     * 设备名称
     */
    @TableField("deviceName")
    private String deviceName;

    /**
     * 设备编号，字符串，唯一性检查
     */
    @TableField("deviceCode")
    private String deviceCode;

    /**
     * 设备归类：信源监测、广电专业、通用服务、传感设备
     */
    @TableField("deviceType")
    private String deviceType;

    /**
     * 设备子类：监测板卡、多画面、编码器。。。。
     */
    @TableField("deviceSubType")
    private String deviceSubType;

    /**
     * 设备图片
     */
    @TableField("imgURL")
    private String imgURL;

    /**
     * 信号类型
     */
    @TableField("signalType")
    private Integer signalType;

    /**
     * 接口数
     */
    @TableField("interfaceCount")
    private Integer interfaceCount;

    /**
     * 所属监测站ID
     */
    @TableField("monitorStationId")
    private Integer monitorStationId;

    /**
     * 关联采集点
     */
    @TableField("logicPositionId")
    private Integer logicPositionId;

    /**
     * 所属机房ID
     */
    @TableField("locateId")
    private Integer locateId;

    /**
     * 机柜编号
     */
    @TableField("cab")
    private String cab;

    /**
     * 机柜位置(自下向上 u数);插卡设备为0
     */
    @TableField("uOfCab")
    private Integer uOfCab;

    /**
     * 插槽位置
     */
    @TableField("slot")
    private Integer slot;

    /**
     * 所属机框ID
     */
    @TableField("parentDeviceId")
    private Integer parentDeviceId;

    /**
     * 资产编号
     */
    @TableField("assetNo")
    private String assetNo;


    /**
     * 管理口IP
     */
    @TableField("controlIP")
    private String controlIP;

    /**
     * 数据口IP，页面设计可增加，逗号分隔存储
     */
    @TableField("dataIPs")
    private String dataIPs;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 同步状态 0：未同步 1：已同步
     */
    @TableField("syncStatus")
    private Integer syncStatus;

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
     * 是否删除：0未删除、1已删除，已删除数据只有系统管理员可以查看和编辑
     */
    @TableField("isDelete")
    private Boolean isDelete;

    /**
     * 通讯参数：串口地址等信息
     */
    @TableField("communicationPara")
    private String communicationPara;

    /**
     * 是否受控 0不受控;1受控
     */
    @TableField("underController")
    private Integer underController;

    /**
     * 网管模式:0：SNMP;1：UDP;2：串口
     */
    @TableField("netMagMode")
    private Integer netMagMode;

    /**
     * 网管模式:1:一体设备;2:机箱;3:插卡设备
     */
    @TableField("deviceStructure")
    private Integer deviceStructure;

    /**
     * 占用U数;插卡设备为0
     */
    @TableField("uOfCost")
    private Integer uOfCost;

    /**
     * 网管采集服务编号
     */
    @TableField("collectServerCode")
    private String collectServerCode;

    /**
     * 开启报警监测：0禁用 1开启
     */
    @TableField("enableAlarmMonitor")
    private Integer enableAlarmMonitor;

    /**
     * 报警数据处理脚本
     */
    @TableField("script")
    private String script;

    /**
     * 设备报警模板映射ID
     */
    @TableField("alarmTemplateId")
    private Integer alarmTemplateId;

    /**
     * 报警上报参数
     */
    @TableField("communicationParaForAlarm")
    private String communicationParaForAlarm;


    /**
     * 占用卡槽
     */
    @TableField("slotOfCost")
    private Integer slotOfCost;

    /**
     * 总卡槽数
     */
    @TableField("slotOfNum")
    private Integer slotOfNum;

    /**
     * 设备高度（U）
     */
    @TableField("uHeight")
    private Integer uHeight;

    /**
     * 生产厂商
     */
    @TableField("productId")
    private Integer productId;

    /**
     * 设备型号
     */
    @TableField("modelId")
    private Integer modelId;


    @TableField(exist = false)
    private String full_deviceSubType;

    @TableField(exist = false)
    private String boardInterfaces_str;

    /**
     * ServerConfig配置url
     */
    @TableField("configurationUrl")
    private String configurationUrl;

    /**
     * 是否是配置模版 0:不是 1:是
     */
    @TableField("isTemplate")
    private Integer isTemplate;

    /**
     * 离线判定门限 单位秒
     */
    @TableField("offlineDecisionThreshold")
    private Integer offlineDecisionThreshold;

}
