package com.figure.core.model.multiview;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 多画面电视墙
 * </p>
 *
 * @author jobob
 * @since 2021-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_videowall_info")
public class MultiviewVideowallInfo extends BaseModel implements Serializable {


    /**
     * 大屏Id
     */
    @TableId(value = "videowallId", type = IdType.AUTO)
    private Integer videowallId;

    /**
     * 大屏名称
     */
    @TableField("videowallName")
    private String videowallName;

    /**
     * 前端Id
     */
    @TableField("frontId")
    private Integer frontId;

    /**
     * 列数
     */
    @TableField("xNum")
    private Integer xNum;

    /**
     * 行数
     */
    @TableField("yNum")
    private Integer yNum;

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
