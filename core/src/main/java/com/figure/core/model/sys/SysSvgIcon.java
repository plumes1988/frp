package com.figure.core.model.sys;

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
 * @since 2023-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_svg_icon")
public class SysSvgIcon implements Serializable {


    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 名字
     */
    @TableField("name")
    private String name;

    /**
     * svg代码
     */
    @TableField("svg")
    private String svg;


}
