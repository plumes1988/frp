package com.figure.core.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 播出系统 ( 逻辑频道 ) 链路图 关联表
 * </p>
 *
 * @author jobob
 * @since 2023-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_logic_channel_chainId_rel")
public class SysLogicChannelChainidRel implements Serializable {


    /**
     * 逻辑频道编号
     */
    @TableField("logicChannelCode")
    private String logicChannelCode;

    /**
     * 链路图Id
     */
    @TableField("chainId")
    private Integer chainId;


}
