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
 * 监控单元接口管理信息
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@Data
@Accessors(chain = true)
@TableName("logic_monitorunit_interface_info")
@EqualsAndHashCode(callSuper = false)
public class LogicMonitorunitInterfaceInfo extends BaseModel {

    /**
     * 切换器接口Id,唯一
     */
    @TableId(type = IdType.AUTO)
    private Integer interfaceId;
    /**
     * 切换器编号
     */
    private String switchCode;
    /**
     * 切换器接口名称
     */
    private String interfaceName;
    /**
     * 接口类型 1输入 2输出
     */
    private Integer interfaceType;
    /**
     * 接口序号
     */
    private Integer interfaceSerialNo;
    /**
     * 接口优先级
     */
    private Integer interfaceRank;
    /**
     * 接口关联采集点
     */
    private Integer logicPosition;
    /**
     * 自动倒换模式 0关闭 1仅录播 2仅直播 3全启用
     */
    private Integer autoEmergency;
    /**
     * 自动回切模式 0关闭 1仅录播 2仅直播 3全启用
     */
    private Integer autoSwitchBack;
    /**
     * 异态判定模式
     */
    private Integer conditionMode;
    /**
     * 权重阈值
     */
    private Integer conditionGrade;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    private Integer isDelete;

}