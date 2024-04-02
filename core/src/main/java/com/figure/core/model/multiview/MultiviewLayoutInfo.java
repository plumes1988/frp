package com.figure.core.model.multiview;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 多画面布局信息
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_layout_info")
public class MultiviewLayoutInfo extends BaseModel implements Serializable {


    /**
     * 布局ID
     */
    @TableId("layoutId")
    private Integer layoutId;

    /**
     * 布局名称
     */
    @TableField("layoutName")
    private String layoutName;

    /**
     * 前端Id，从别的前端获取播放地址
     */
    @TableField("frontId")
    private Integer frontId;

    /**
     * 分辨率,例如1920*1080
     */
    @TableField("resolution")
    private String resolution;

    /**
     * 媒体类型：1视频多画面；2音频多画面，自动设置子窗口类型
     */
    @TableField("mediaType")
    private Integer mediaType;

    /**
     * 是否推流：0-否；1-是
     */
    @TableField("pushStream")
    private Integer pushStream;

    /**
     * 推流地址
     */
    @TableField("streamUrl")
    private String streamUrl;

    /**
     * 推流码率（Kbps）
     */
    @TableField("streamCodeRate")
    private Integer streamCodeRate;

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


}
