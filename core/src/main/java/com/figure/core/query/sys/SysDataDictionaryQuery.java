package com.figure.core.query.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.figure.core.model.sys.SysDataDictionary;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import lombok.Data;

/**
 * <p>
 * 系统数据字典表
 * </p>
 *
 * @author jobob
 * @since 2021-03-29
 */
@Data
public class SysDataDictionaryQuery extends AbstractQuery<SysDataDictionary> {


    @Eq
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 参数名称
     */
    @Eq
    @TableField("paraName")
    private String paraName;

    /**
     * 参数类别
     */
    @Eq
    @TableField("paraType")
    private String paraType;

    /**
     * 参数值
     */
    @Eq
    @TableField("paraValue")
    private String paraValue;

    /**
     * 父级参数值
     */
    @Eq
    @TableField("parentParaType")
    private String parentParaType;

    /**
     * 父级参数类别
     */
    @Eq
    @TableField("parentParaValue")
    private String parentParaValue;

    /**
     * 补充数据
     */
    @Eq
    @TableField("paraData")
    private String paraData;

    /**
     * 备注
     */
    @Eq
    private String remark;

    /**
     * 是否启用：0:未删除、1:删除，已删除数据只有系统管理员可以查看和编辑
     */
    @Eq
    @TableField("isDelete")
    private Integer isDelete;

}
