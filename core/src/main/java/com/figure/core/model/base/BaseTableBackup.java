package com.figure.core.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_table_backup")
public class BaseTableBackup implements Serializable {


    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 表名
     */
    @TableField("tablename")
    private String tablename;

    /**
     * 筛选字段
     */
    @TableField("dataName")
    private String dataName;

    /**
     * 筛选条件
     */
    @TableField("conditions")
    private String conditions;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

}
