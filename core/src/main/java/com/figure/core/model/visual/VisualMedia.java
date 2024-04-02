package com.figure.core.model.visual;

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
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("visual_media")
public class VisualMedia implements Serializable {

    /**
     * 媒体ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @TableField("name")
    private String name;

    @TableField("type")
    private String type;

    @TableField("size")
    private Double size;

    @TableField("resolution")
    private String resolution;

    @TableField("length")
    private Double length;

    @TableField("url")
    private String url;

    @TableField("tags")
    private String tags;


}
