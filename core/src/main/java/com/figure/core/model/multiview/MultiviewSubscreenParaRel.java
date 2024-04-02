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
 * 子窗口属性关联表
 * </p>
 *
 * @author jobob
 * @since 2021-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("multiview_subscreen_para_rel")
public class MultiviewSubscreenParaRel implements Serializable,Cloneable {


    /**
     * 子窗口Id
     */
    @TableField(value = "subScreenId")
    private Integer subScreenId;

    /**
     * 属性Id
     */
    @TableField("paraId")
    private Integer paraId;

    /**
     * 属性值
     */
    @TableField("paraValue")
    private String paraValue;



    @TableField(exist = false)
    private MultiviewSubscreenPara multiviewSubscreenPara;


}
