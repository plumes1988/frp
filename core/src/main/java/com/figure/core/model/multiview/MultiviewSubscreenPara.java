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
 * 子窗口属性管理
 * </p>
 *
 * @author jobob
 * @since 2021-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_subscreen_para")
public class MultiviewSubscreenPara extends BaseModel implements Serializable {


    /**
     * 子屏Id,自增
     */
    @TableId(value = "paraId", type = IdType.AUTO)
    private Integer paraId;

    /**
     * 关联子屏类型
     */
    @TableField("subScreenType")
    private String subScreenType;

    /**
     * 属性名称，用于显示
     */
    @TableField("paraName")
    private String paraName;

    /**
     * 设置模式：0开关、1文本、2调色板
     */
    @TableField("paraType")
    private String paraType;

    /**
     * 默认值
     */
    @TableField("paraValue")
    private String paraValue;

    /**
     * 多画面配置属性
     */
    @TableField("communicationFeld")
    private String communicationFeld;

    /**
     * 排序
     */
    @TableField("sortNo")
    private Integer sortNo;

    /**
     * 描述
     */
    @TableField("remark")
    private String remark;

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



    /**
     * 等于条件下关联ID
     */
    @TableField("equalRelParaIds")
    private String equalRelParaIds;


    /**
     * 等于值
     */
    @TableField("equalRelValues")
    private String equalRelValues;

    /**
     * 数据源
     */
    @TableField("dataSource")
    private String dataSource;


    /**
     * 参数位置
     */
    @TableField("pramsPostion")
    private String pramsPostion;


}
