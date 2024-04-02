package com.figure.core.query.device;

import com.figure.core.model.device.DeviceInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceInfoQuery extends AbstractQuery<DeviceInfo> {

    @Eq
    @ApiModelProperty("设备Id")
    private Integer deviceId;

    @Eq
    @ApiModelProperty("设备名称")
    private String deviceName;

    @Eq
    @ApiModelProperty("设备编号，字符串，唯一性检查")
    private String deviceCode;

    @Eq
    @ApiModelProperty("设备归类：信源监测、广电专业、通用服务、传感设备")
    private Integer deviceType;

    @Eq
    @ApiModelProperty("设备子类：监测板卡、多画面、编码器。。。。")
    private Integer deviceSubType;

    @Eq
    @ApiModelProperty("设备型号")
    private String deviceModelType;

    @Eq
    @ApiModelProperty("设备图片")
    private String imgURL;

    @Eq
    @ApiModelProperty("信号类型")
    private Integer signalType;

    @Eq
    @ApiModelProperty("接口数")
    private Integer interfaceCount;

    @Eq
    @ApiModelProperty("所属监测站ID")
    private Integer monitorStationId;

    @Eq
    @ApiModelProperty("关联采集点")
    private Integer logicPositionId;

    @Eq
    @ApiModelProperty("所属机房ID")
    private Integer locateId;

    @Eq
    @ApiModelProperty("机柜编号")
    private String cab;

    @Eq
    @ApiModelProperty("机柜位置（U数)")
    private Integer uOfCab;

    @Eq
    @ApiModelProperty("插槽位置")
    private Integer slot;

    @Eq
    @ApiModelProperty("所属机框ID")
    private Integer parentDeviceId;

    @Eq
    @ApiModelProperty("设备序列号")
    private String serialNo;

    @Eq
    @ApiModelProperty("资产编号")
    private String assetNo;

    @Eq
    @ApiModelProperty("购买日期")
    private Date buyDate;

    @Eq
    @ApiModelProperty("开始使用日期")
    private Date useDate;

    @Eq
    @ApiModelProperty("管理口IP")
    private String controlIP;

    @Eq
    @ApiModelProperty("数据口IP，页面设计可增加，逗号分隔存储")
    private String dataIPs;

    @Eq
    @ApiModelProperty("备注")
    private String remark;

    @Eq
    @ApiModelProperty("同步状态 0：未同步 1：已同步")
    private Integer syncStatus;

    @Eq
    @ApiModelProperty("0：使用中、1：维护中、2：停用")
    private Integer useStatus;

    @Eq
    @ApiModelProperty("是否删除：0未删除、1已删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;

    @Eq
    @ApiModelProperty("生产厂商")
    private String producer;

    @Eq
    @ApiModelProperty("额定功率")
    private String ratedPower;

    @Eq
    @ApiModelProperty("重量")
    private Long weight;

    @Eq
    @ApiModelProperty("3D建模ID")
    private String threeDModelId;

    @Eq
    @ApiModelProperty("通讯参数：串口地址等信息")
    private String communicationPara;

    @Eq
    @ApiModelProperty("是否受控 0不受控;1受控")
    private Long underController;

    @Eq
    @ApiModelProperty("网管模式:0：SNMP;1：UDP;2：串口")
    private Long netMagMode;

}