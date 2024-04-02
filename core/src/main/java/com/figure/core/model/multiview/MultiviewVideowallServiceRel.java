package com.figure.core.model.multiview;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 多画面电视墙与多画面服务关联
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_videowall_service_rel")
public class MultiviewVideowallServiceRel implements Serializable {


    /**
     * 视频墙Id
     */
    @TableField(value = "videoWallId")
    private Integer videoWallId;

    /**
     * 多画面服务ID
     */
    @TableField("multiviewServiceId")
    private Integer multiviewServiceId;

    /**
     * 多画面屏幕输出ID
     */
    @TableField("screenId")
    private Integer screenId;

    /**
     * 第几列
     */
    @TableField("xPos")
    private Integer xPos;

    /**
     * 第几行
     */
    @TableField("yPos")
    private Integer yPos;


}
