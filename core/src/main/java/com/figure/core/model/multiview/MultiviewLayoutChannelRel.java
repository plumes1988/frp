package com.figure.core.model.multiview;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 多画面布局与频道关联表
 * </p>
 *
 * @author jobob
 * @since 2021-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_layout_channel_rel")
public class MultiviewLayoutChannelRel implements Serializable {


    /**
     * 布局Id
     */
    @TableId("layoutId")
    private Integer layoutId;

    /**
     * 频道Id
     */
    @TableField("channelId")
    private String channelId;

    /**
     * 节目播放地址
     */
    @TableField("streamURL")
    private String streamURL;


}
