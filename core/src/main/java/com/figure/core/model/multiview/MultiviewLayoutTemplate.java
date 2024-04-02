package com.figure.core.model.multiview;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 多画面布局模板
 * </p>
 *
 * @author jobob
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_layout_template")
public class MultiviewLayoutTemplate extends BaseModel implements Serializable {


    /**
     * 多画面布局模板ID
     */
    @TableId(value = "layoutTemplateId", type = IdType.AUTO)
    private Integer layoutTemplateId;

    /**
     * 多画面大屏布局模板名称
     */
    @TableField("layoutTemplateName")
    private String layoutTemplateName;

    /**
     * 详细布局JSON
     */
    @TableField("layoutDetail")
    private String layoutDetail;

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
