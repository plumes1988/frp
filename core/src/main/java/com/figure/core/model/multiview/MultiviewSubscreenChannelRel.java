package com.figure.core.model.multiview;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 子窗口与频道关系表
 * </p>
 *
 * @author jobob
 * @since 2021-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_subscreen_channel_rel")
public class MultiviewSubscreenChannelRel implements Serializable {


    /**
     * 子窗口Id
     */
    @TableField("subScreenId")
    private Integer subScreenId;

    /**
     * 频道Id
     */
    @TableField("channelId")
    private String channelId;

    /**
     * 频道名称
     */
    @TableField("channelName")
    private String channelName;

    /**
     * 监测点名称
     */
    @TableField("monitorpointName")
    private String monitorpointName;

    /**
     * 排序
     */
    @TableField("sortNo")
    private Integer sortNo;


}
