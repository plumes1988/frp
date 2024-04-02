package com.figure.core.model.visual;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("visual_bigScreen")
public class VisualBigscreen implements Serializable {


    @TableId(value = "bigScreenId", type = IdType.AUTO)
    private Integer bigScreenId;

    @TableField("name")
    private String name;

    @TableField("poster")
    private String poster;

    @TableField("mark")
    private String mark;

    @TableField("config")
    private String config;


}
