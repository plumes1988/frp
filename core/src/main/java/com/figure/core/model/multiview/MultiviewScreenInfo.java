package com.figure.core.model.multiview;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 多画面服务输出屏幕信息
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_screen_info")
public class MultiviewScreenInfo implements Serializable {


    /**
     * 多画面屏幕输出ID
     */
    @TableId(value = "screenId", type = IdType.AUTO)
    private Integer screenId;

    /**
     * 多画面服务ID
     */
    @TableField("multiviewServiceId")
    private Integer multiviewServiceId;

    /**
     * 显示输出位置：1（单屏、双屏1、四屏1）、2（双屏2、四屏2）、3四屏3、4四屏4
     */
    @TableField("multiviewScreenPos")
    private Integer multiviewScreenPos;

    /**
     * 关联布局ID，为空表示未关联
     */
    @TableField("layoutId")
    private Integer layoutId;


}
