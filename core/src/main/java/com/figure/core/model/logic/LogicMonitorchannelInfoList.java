package com.figure.core.model.logic;

import lombok.Data;


/**
 * <p>
 * 监控频道管理信息
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@Data
public class LogicMonitorchannelInfoList extends LogicMonitorchannelInfo {
    private Integer unitId;
    /**
     * 监控单元名称
     */
    private String monitorName;
    /**
     * 监控单元简称
     */
    private String monitorAlias;
    /**
     * 关联切换器编号，从设备关联选择
     */
    private String deviceCode;
    /**
     * 是否联动切换器 0无关联 1关联
     */
    private Integer linkSwitch;
    /**
     * 联动切换器的编号，从设备关联选择
     */
    private Integer linkSwitchCode;
    /**
     * 监控单元模式
     */
    private Integer unitMode;
    /**
     * 输入接口数
     */
    private Integer inputCount;
    /**
     * 输出接口数
     */
    private Integer outputCount;
}