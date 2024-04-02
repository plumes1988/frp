package com.figure.core.model.logic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 监控单元信息
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@Data
@Accessors(chain = true)
@TableName("logic_monitorunit_info")
@EqualsAndHashCode(callSuper = false)
public class LogicMonitorunitInfoList extends BaseModel {

    /**
     * 是否关联切换器0无关联 1关联
     */
    @TableId(type = IdType.AUTO)
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
    private String switchCode;
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
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    private Integer isDelete;

    private String monitorChannelCode;

    private String logicChannelCode;

    private String channelName;

    private String switchName;

    /**
     * 和逻辑分析组关联的播出系统编号
     */
    private String channelCode;

    /**
     * 和逻辑分析组关联的播出系统名称
     */
    private String channelCodeName;
}