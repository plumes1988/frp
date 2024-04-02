package com.figure.op.dict.domain;


import com.baomidou.mybatisplus.annotation.*;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据表
 *
 * @author ly
 */
@TableName("sys_dict_data")
@KeySequence("sys_dict_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataDO extends BaseEntity {

    /**
     * 字典数据编号
     */
    @TableId
    private Long id;
    /**
     * 字典排序
     */
    @TableField("sort")
    private Integer sort;
    /**
     * 字典标签
     */
    @TableField("label")
    private String label;
    /**
     * 字典值
     */
    @TableField("value")
    private String value;
    /**
     * 字典类型
     */
    @TableField("dictType")
    private String dictType;
    /**
     * 状态
     * 0=开启 1=关闭
     */
    @TableField("status")
    private Integer status;
    /**
     * 颜色类型
     * element_ui可使用
     */
    @TableField("colorType")
    private String colorType;
    /**
     * css 样式
     */
    @TableField("cssClass")
    private String cssClass;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;
}
