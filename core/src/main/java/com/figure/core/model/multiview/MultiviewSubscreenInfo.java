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
 * 子窗口信息
 * </p>
 *
 * @author jobob
 * @since 2021-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_subscreen_info")
public class MultiviewSubscreenInfo implements Serializable,Cloneable {

    /**
     * 子屏Id,自增
     */
    @TableId(value = "subScreenId", type = IdType.AUTO)
    private Integer subScreenId;

    /**
     * 子画面名称,通常默认为关联的频道名称
     */
    @TableField("subScreenName")
    private String subScreenName;

    /**
     * 关联布局Id
     */
    @TableField("layoutId")
    private Integer layoutId;

    /**
     * 子窗口分辨率,以*分隔
     */
    @TableField("resolution")
    private String resolution;

    /**
     * 起始坐标,以,分隔
     */
    @TableField("positionXY")
    private String positionXY;

    /**
     * 子屏类型(1：视频，2：音频，3：视频比对，4：音频比对，5：数字时钟，6：图片，7:频谱)
     */
    @TableField("subScreenType")
    private Integer subScreenType;

    /**
     * 与该子画面比对的子画面Id
     */
    @TableField("cpSubScreenId")
    private Integer cpSubScreenId;

    /**
     * 视频类型：0：标清、1：高清、2：4K，默认高清
     */
    @TableField("videoType")
    private Integer videoType;

    /**
     * 比对区域，视频比对画面有效
     */
    @TableField("compareArea")
    private String compareArea;

    /**
     * 节目输入网口IP
     */
    @TableField("inputIP")
    private String inputIP;

    /**
     * tally条文字颜色
     */
    @TableField("charColor")
    private String charColor;

    /**
     * tally条文字背景颜色
     */
    @TableField("charBackColor")
    private String charBackColor;

    /**
     * 节目轮询间隔（秒）
     */
    @TableField("pollingTime")
    private Boolean pollingTime;

    /**
     * 图片路径
     */
    @TableField("imageURL")
    private String imageURL;

    @TableField(exist = false)
    private int mark_for_multScreen = 0;


}
