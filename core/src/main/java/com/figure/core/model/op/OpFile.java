package com.figure.core.model.op;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("op_file")
public class OpFile extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "fileId", type = IdType.AUTO)
    private Integer fileId;

    /**
     * 文件名
     */
    @TableField("fileName")
    private String fileName;

    /**
     * 文件目录地址
     */
    @TableField("fileUrl")
    private String fileUrl;

    /**
     * 1、图纸 2、手册 3、技术资料
     */
    @TableField("filetype")
    private String filetype;

    /**
     * 设备厂商
     */
    @TableField("productId")
    private Integer productId;

    /**
     * 设备型号
     */
    @TableField("modelId")
    private Integer modelId;

    /**
     * 描述
     */
    @TableField("mark")
    private String mark;

    /**
     * 设备大类
     */
    @TableField("deviceTypeCode")
    private Integer deviceTypeCode;

    /**
     * 设备子类
     */
    @TableField("deviceSubTypeCode")
    private Integer deviceSubTypeCode;


    /**
     * 0:未删除；1:已删除
     */
    @TableField("isDelete")
    private Integer isDelete;


}
