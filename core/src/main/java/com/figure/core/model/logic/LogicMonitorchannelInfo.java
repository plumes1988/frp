package com.figure.core.model.logic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 监控频道管理信息
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@Data
@Accessors(chain = true)
@TableName("logic_monitorchannel_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicMonitorchannelInfo", description = "监控频道管理信息")
public class LogicMonitorchannelInfo extends BaseModel {

    /**
     * 监控频道编号
     */
    @TableId(type = IdType.INPUT)
    private String monitorChannelCode;
    /**
     * 监控频道名称
     */
    private String monitorChannelName;
    /**
     * 监控频道简称
     */
    private String monitorChannelAlias;
    /**
     * 0自动比对 1末级比对 2源木比对 3比对关闭
     */
    private Integer comparisonMode;
    /**
     * 0关闭 1仅录播 2仅直播 3全部开启
     */
    private Integer autoEmergency;
    /**
     * 0关闭 1启用
     */
    private Integer autoSwitchBack;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    private Integer isDelete;

}