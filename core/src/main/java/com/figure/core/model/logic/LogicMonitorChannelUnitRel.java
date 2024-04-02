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
 * 监控频道与监控单元关联
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@Data
@Accessors(chain = true)
@TableName("logic_monitor_channel_unit_rel")
@EqualsAndHashCode(callSuper = false)
public class LogicMonitorChannelUnitRel extends BaseModel {

    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 监控频道编号
     */
    private String monitorChannelCode;
    /**
     * 监控单元Id
     */
    private Integer unitId;

}